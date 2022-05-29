package itmo.mpi.service;

import itmo.mpi.model.profiles.CrewProfileResponse;
import itmo.mpi.model.profiles.ShipProfileResponse;
import itmo.mpi.model.profiles.UserProfileResponse;

import java.util.List;

public interface ProfilesService {

    UserProfileResponse getCurrentUserProfile(String nickName);

    List<ShipProfileResponse> getShipsForCurrentUser(String nickname);

    List<CrewProfileResponse> getCrewsForCurrentUser(String nickname);
}
