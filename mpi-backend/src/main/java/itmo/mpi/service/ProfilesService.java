package itmo.mpi.service;

import itmo.mpi.model.profiles.*;

import java.util.List;

public interface ProfilesService {

    CrewResponse registerCrew(String nickName, CrewRequest newCrew);

    ShipResponse registerShip(String nickName, ShipRequest newShip);

    UserProfileResponse getCurrentUserProfile(String nickName);

    List<ShipProfileResponse> getShipsForCurrentUser(String nickname);

    List<CrewProfileResponse> getCrewsForCurrentUser(String nickname);

    CrewResponse getUserCrew(String nickname);

    ShipResponse getUserShip(String nickName);
}
