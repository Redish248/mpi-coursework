package itmo.mpi.service;

import itmo.mpi.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAllNotActivatedUsers();

    User createUser(String name, String surname, String nick, String password, String birth_date, String user_type, String email, String phone);
}
