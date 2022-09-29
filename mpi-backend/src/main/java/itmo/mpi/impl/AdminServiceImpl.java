package itmo.mpi.impl;

import itmo.mpi.entity.Admin;
import itmo.mpi.entity.User;
import itmo.mpi.exception.UserAlreadyExistException;
import itmo.mpi.repository.AdminRepository;
import itmo.mpi.repository.UserRepository;
import itmo.mpi.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    private final UserRepository userRepository;

    @Override
    public void processUser(String nick, boolean isActivated) {
        User user = userRepository.findByNick(nick);
        if (isActivated) {
            user.setIsActivated(true);
            userRepository.save(user);
        } else {
            userRepository.delete(user);
        }
    }

    @Override
    public Admin createAdmin(String name, String surname, String nick, String password, int salary) {
        User user = userRepository.findByNick(nick);
        Admin oldAdmin = adminRepository.findAdminByNick(nick);
        if (user != null || oldAdmin != null) {
            throw new UserAlreadyExistException(nick);
        }
        Admin newAdmin = new Admin();
        newAdmin.setName(name);
        newAdmin.setSurname(surname);
        newAdmin.setNick(nick);
        newAdmin.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        newAdmin.setSalary(salary);
        return adminRepository.save(newAdmin);
    }
}
