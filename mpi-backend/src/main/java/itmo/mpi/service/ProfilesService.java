package itmo.mpi.service;

import itmo.mpi.model.profiles.CrewProfileResponse;
import itmo.mpi.model.profiles.ShipProfileResponse;

import java.util.List;

public interface ProfilesService {

    List<ShipProfileResponse> getShipsForCurrentUser(String nickname);

    List<CrewProfileResponse> getCrewsForCurrentUser(String nickname);
}
