package itmo.mpi.service;

import itmo.mpi.entity.User;
import itmo.mpi.model.UserInfo;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    List<UserInfo> findAllNotActivatedUsers();

    User createUser(UserInfo newUser);

    void removeOldRegistrationRequests(LocalDate date);
}
