package itmo.mpi.service;

import itmo.mpi.entity.Admin;

public interface AdminService {
    void processUser(String nick, boolean isActivated);

    Admin createAdmin(String name, String surname, String nick, String password, int salary);
}
