package itmo.mpi.impl;

import itmo.mpi.entity.Crew;
import itmo.mpi.entity.CrewMember;
import itmo.mpi.entity.Ship;
import itmo.mpi.entity.User;
import itmo.mpi.model.profiles.CrewProfileResponse;
import itmo.mpi.model.profiles.ShipProfileResponse;
import itmo.mpi.model.profiles.UserProfileResponse;
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
                user.getIsVip()
        );
    }

    @Override
    public List<ShipProfileResponse> getShipsForCurrentUser(String nickname) {
        User currentUser = userRepository.findByNick(nickname);

        return userRepository
                .findAll().stream()
                .filter(el -> Objects.equals(el.getUserType().getName(), SHIP_OWNER) && el.getIsActivated())
                .map(el -> {
                    Ship ship = shipRepository.getShipByOwnerId(el.getId());
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
                    Crew crew = crewRepository.getCrewByCrewOwner(el);
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

}
