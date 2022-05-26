package itmo.mpi.service;

import itmo.mpi.entity.User;
import itmo.mpi.model.ProfilesResponse;
import itmo.mpi.model.profiles.CrewProfileResponse;
import itmo.mpi.model.profiles.ShipProfileResponse;
import itmo.mpi.model.UserInfo;

import java.util.List;

public interface UserService {
    List<UserInfo> findAllNotActivatedUsers();

    User createUser(UserInfo newUser);
}
