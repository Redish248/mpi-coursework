package itmo.mpi.impl;

import itmo.mpi.entity.Crew;
import itmo.mpi.entity.CrewMember;
import itmo.mpi.entity.Ship;
import itmo.mpi.entity.User;
import itmo.mpi.exception.IllegalRequestParamsException;
import itmo.mpi.model.profiles.*;
import itmo.mpi.repository.CrewMemberRepository;
import itmo.mpi.repository.CrewRepository;
import itmo.mpi.repository.ShipRepository;
import itmo.mpi.repository.UserRepository;
import itmo.mpi.service.ProfilesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    @Override
    public CrewResponse registerCrew(String nickName, CrewRequest newCrew) {
        User user = userRepository.findByNick(nickName);
        if (!Objects.equals(user.getUserType().getName(), CREW_MANAGER)) throw new IllegalRequestParamsException("");
        Crew crewEntity = new Crew(
                newCrew.getTeamName(),
                user,
                newCrew.getPricePerDay(),
                newCrew.getPhoto(),
                newCrew.getDescription()
        );

        Crew savedCrew = crewRepository.save(crewEntity);

        List<CrewMember> members = newCrew.getMembers().stream().map(m -> new CrewMember(savedCrew, m.getFullName(), m.getExperience())).collect(Collectors.toList());
        crewMemberRepository.saveAll(members);

        return new CrewResponse(savedCrew, members);
    }

    @Override
    public ShipResponse registerShip(String nickName, ShipRequest newShip) {
        User user = userRepository.findByNick(nickName);
        if (!Objects.equals(user.getUserType().getName(), SHIP_OWNER)) throw new IllegalRequestParamsException("");

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
                .collect(Collectors.toList());
    }

    @Override
    public List<CrewProfileResponse> getCrewsForCurrentUser(String nickname) {
        User currentUser = userRepository.findByNick(nickname);

        return userRepository
                .findAll().stream()
                .filter(el -> Objects.equals(el.getUserType().getName(), CREW_MANAGER) && el.getIsActivated())
                .map(el -> {
                    Crew crew = getCrew(el);
                    if (crew == null) return null;

                    List<CrewMember> members = crewMemberRepository.getCrewMemberByCrewId(crew.getId());

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
                .collect(Collectors.toList());
    }

    @Override
    public CrewResponse getUserCrew(String nickname) {
        User user = userRepository.findByNick(nickname);
        if (!Objects.equals(user.getUserType().getName(), CREW_MANAGER))
            throw new IllegalRequestParamsException("incorrect user type");

        Crew crew = getCrew(user);
        if (crew == null) return null;

        List<CrewMember> members = crewMemberRepository.getCrewMemberByCrewId(crew.getId());
        return new CrewResponse(crew, members);
    }

    @Override
    public ShipResponse getUserShip(String nickName) {
        User user = userRepository.findByNick(nickName);
        if (!Objects.equals(user.getUserType().getName(), SHIP_OWNER))
            throw new IllegalRequestParamsException("incorrect user type");

        Ship ship = getShip(user);

        return new ShipResponse(ship);
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
