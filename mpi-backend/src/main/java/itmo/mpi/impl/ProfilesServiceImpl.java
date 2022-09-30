package itmo.mpi.impl;

import itmo.mpi.entity.Crew;
import itmo.mpi.entity.CrewMember;
import itmo.mpi.entity.Ship;
import itmo.mpi.entity.User;
import itmo.mpi.exception.IllegalRequestParamsException;
import itmo.mpi.exception.UserAlreadyHasCrewException;
import itmo.mpi.exception.UserAlreadyHasShipException;
import itmo.mpi.model.UserInfoUpdate;
import itmo.mpi.model.profiles.CrewMemberRequest;
import itmo.mpi.model.profiles.CrewProfileResponse;
import itmo.mpi.model.profiles.CrewRequest;
import itmo.mpi.model.profiles.CrewResponse;
import itmo.mpi.model.profiles.ShipProfileResponse;
import itmo.mpi.model.profiles.ShipRequest;
import itmo.mpi.model.profiles.ShipResponse;
import itmo.mpi.model.profiles.UserProfileResponse;
import itmo.mpi.repository.CrewMemberRepository;
import itmo.mpi.repository.CrewRepository;
import itmo.mpi.repository.ShipRepository;
import itmo.mpi.repository.UserRepository;
import itmo.mpi.service.ProfilesService;
import itmo.mpi.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfilesServiceImpl implements ProfilesService {
    private final String SHIP_OWNER = "SHIP_OWNER";
    private final String CREW_MANAGER = "CREW_MANAGER";

    private final UserRepository userRepository;
    private final ShipRepository shipRepository;
    private final CrewRepository crewRepository;
    private final CrewMemberRepository crewMemberRepository;
    private final CommonUtils commonUtils;

    @Override
    public CrewResponse registerCrew(String nickName, CrewRequest newCrew) {
        User user = userRepository.findByNick(nickName);
        if (!Objects.equals(user.getUserType().getName(), CREW_MANAGER)) {
            throw new IllegalRequestParamsException("Incorrect role type");
        }
        if (getCrew(user) != null) {
            throw new UserAlreadyHasCrewException();
        }

        Crew crewEntity = new Crew(
                newCrew.getTeamName(),
                user,
                newCrew.getPricePerDay(),
                newCrew.getPhoto(),
                newCrew.getDescription()
        );

        Crew savedCrew = crewRepository.save(crewEntity);

        List<CrewMember> members = newCrew.getMembers().stream().map(m -> new CrewMember(savedCrew, m.getFullName(),
                m.getExperience())).collect(Collectors.toList());
        members = crewMemberRepository.saveAll(members);

        return new CrewResponse(savedCrew, members);
    }

    @Override
    public CrewResponse updateCrew(String nickName, CrewRequest newCrew) {
        User user = userRepository.findByNick(nickName);
        Crew crew = crewRepository.getCrewByCrewOwner(user);

        List<CrewMemberRequest> shortModelMembers = new ArrayList<>();

        crew.setTeamName(newCrew.getTeamName());
        crew.setPhoto(newCrew.getPhoto());
        crew.setDescription(newCrew.getDescription());
        crew.setPricePerDay(newCrew.getPricePerDay());
        Crew savedCrew = crewRepository.save(crew);

        crewMemberRepository.getCrewMembersByCrewId(crew.getId()).forEach(oldMember -> {
            CrewMemberRequest shortModel = convertToShortModel(oldMember);
            if (!newCrew.getMembers().contains(shortModel)) {
                crewMemberRepository.delete(oldMember);
            } else {
                shortModelMembers.add(shortModel);
            }
        });
        //FIXME: тут при сохранении id в базе почти всегда по идее меняется.
        // Может, адекватный способ апдейта найдётся
        newCrew.getMembers().forEach(newMember -> {
            if (!shortModelMembers.contains(newMember)) {
                crewMemberRepository.save(new CrewMember(crew, newMember.getFullName(),
                        newMember.getExperience()));
                shortModelMembers.add(newMember);
            }
        });

        return new CrewResponse(savedCrew, crewMemberRepository.getCrewMembersByCrewId(crew.getId()));
    }

    private CrewMemberRequest convertToShortModel(CrewMember oldMember) {
        CrewMemberRequest oldModel = new CrewMemberRequest();
        oldModel.setFullName(oldMember.getFullName());
        oldModel.setExperience(oldMember.getExperience());
        return oldModel;
    }

    @Override
    public ShipResponse registerShip(String nickName, ShipRequest newShip) {
        User user = userRepository.findByNick(nickName);
        if (!Objects.equals(user.getUserType().getName(), SHIP_OWNER)) {
            throw new IllegalRequestParamsException("incorrect role");
        }

        if (getShip(user) != null) throw new UserAlreadyHasShipException();

        Ship shipEntity = new Ship(
                newShip.getName(),
                user,
                newShip.getSpeed(),
                newShip.getCapacity(),
                newShip.getFuelConsumption(),
                newShip.getLength(),
                newShip.getWidth(),
                newShip.getPricePerDay(),
                newShip.getPhoto(),
                newShip.getDescription()
        );

        Ship savedShip = shipRepository.save(shipEntity);
        return new ShipResponse(savedShip);
    }

    @Override
    public ShipResponse updateShip(String nickName, ShipRequest newShipInfo) {
        User user = userRepository.findByNick(nickName);
        Ship ship = shipRepository.getShipByOwnerId(user.getId());
        ship.setName(newShipInfo.getName());
        ship.setPhoto(newShipInfo.getPhoto());
        ship.setPricePerDay(newShipInfo.getPricePerDay());
        ship.setDescription(newShipInfo.getDescription());
        ship.setLength(newShipInfo.getLength());
        ship.setWidth(newShipInfo.getWidth());
        ship.setFuelConsumption(newShipInfo.getFuelConsumption());
        ship.setSpeed(newShipInfo.getSpeed());
        ship.setCapacity(newShipInfo.getCapacity());
        return new ShipResponse(shipRepository.save(ship));
    }

    @Override
    public UserProfileResponse getCurrentUserProfile(String nickName) {
        User user = userRepository.findByNick(nickName);
        return new UserProfileResponse(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getNick(),
                user.getBirthDate().toString(),
                user.getEmail(),
                user.getPhone(),
                user.isShareContactInfo(),
                user.getIsVip(),
                user.getIsActivated()
        );
    }

    @Override
    public List<ShipProfileResponse> getShipsForCurrentUser(String nickname) {
        User currentUser = userRepository.findByNick(nickname);

        return userRepository
                .findAll().stream()
                .filter(el -> Objects.equals(el.getUserType().getName(), SHIP_OWNER) && el.getIsActivated())
                .map(el -> {
                    Ship ship = getShip(el);
                    if (ship == null) return null;
                    return new ShipProfileResponse(
                            el.getId(),
                            el.getName(),
                            el.getSurname(),
                            el.isShareContactInfo() ? el.getEmail() : null,
                            el.isShareContactInfo() ? el.getPhone() : null,
                            currentUser.getIsVip() ? el.getIsPirate() : null,

                            ship
                    );
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<CrewProfileResponse> getCrewsForCurrentUser(String nickname) {
        User currentUser = userRepository.findByNick(nickname);

        return userRepository
                .findAll().stream()
                .filter(el -> Objects.equals(el.getUserType().getName(), CREW_MANAGER)
                        && el.getIsActivated())
                .map(el -> {
                    Crew crew = getCrew(el);
                    if (crew == null) return null;

                    List<CrewMember> members = crewMemberRepository.getCrewMembersByCrewId(crew.getId());

                    return new CrewProfileResponse(
                            el.getId(),
                            el.getName(),
                            el.getSurname(),
                            el.isShareContactInfo() ? el.getEmail() : null,
                            el.isShareContactInfo() ? el.getPhone() : null,
                            currentUser.getIsVip() ? el.getIsPirate() : null,
                            crew,
                            members
                    );
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public CrewResponse getUserCrew(String nickname) {
        User user = userRepository.findByNick(nickname);
        if (!Objects.equals(user.getUserType().getName(), CREW_MANAGER))
            throw new IllegalRequestParamsException("incorrect user type");

        Crew crew = getCrew(user);
        if (crew == null) return null;

        List<CrewMember> members = crewMemberRepository.getCrewMembersByCrewId(crew.getId());
        return new CrewResponse(crew, members);
    }

    @Override
    public ShipResponse getUserShip(String nickName) {
        User user = userRepository.findByNick(nickName);
        if (!Objects.equals(user.getUserType().getName(), SHIP_OWNER))
            throw new IllegalRequestParamsException("incorrect user type");

        Ship ship = getShip(user);
        return ship == null ? null : new ShipResponse(ship);
    }

    @Override
    public User updateUser(UserInfoUpdate userInfo) {
        User user = userRepository.findByNick(commonUtils.getCurrentUser().getName());
        user.setName(userInfo.getName());
        user.setSurname(userInfo.getSurname());
        user.setShareContactInfo(userInfo.isShareContacts());
        user.setPhone(userInfo.getPhone());
        user.setEmail(userInfo.getEmail());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(userInfo.getBirthDate(), formatter);
        user.setBirthDate(localDate);
        User userToReturn = userRepository.save(user);
        userToReturn.setPassword("");
        return userToReturn;
    }

    private Crew getCrew(User user) {
        try {
            return crewRepository.getCrewByCrewOwner(user);
        } catch (NullPointerException e) {
            return null;
        }
    }

    private Ship getShip(User user) {
        try {
            return shipRepository.getShipByOwnerId(user.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }
}
