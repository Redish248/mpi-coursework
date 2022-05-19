package itmo.mpi.service;

import itmo.mpi.entity.User;
import itmo.mpi.model.UserInfo;

import java.util.List;

public interface UserService {
    List<User> findAllNotActivatedUsers();

    User createUser(UserInfo newUser);
}
