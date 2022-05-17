package itmo.mpi.impl;

import itmo.mpi.entity.Admin;
import itmo.mpi.entity.User;
import itmo.mpi.exception.UserAlreadyExistException;
import itmo.mpi.repository.AdminRepository;
import itmo.mpi.repository.UserRepository;
import itmo.mpi.repository.UserRoleRepository;
import itmo.mpi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final AdminRepository adminRepository;

    @Override
    public List<User> findAllNotActivatedUsers() {
        return userRepository.findUsersByIsActivated(false);
    }

    @Override
    public User createUser(String name, String surname, String nick, String password, String birth_date, String user_type, String email, String phone) {
        User user = userRepository.findUsersByNick(nick);
        Admin oldAdmin = adminRepository.findAdminByNick(nick);
        if (user != null || oldAdmin != null) {
            throw new UserAlreadyExistException(nick);
        }
        User newUser = new User();
        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setNick(nick);
        newUser.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        //FIXME:
        //newUser.setBirthDate();
        newUser.setUserType(userRoleRepository.findUserRoleByName(user_type));
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setIsActivated(false);
        newUser.setIsVip(false);
        newUser.setIsPirate(false);
        return userRepository.save(newUser);
    }


}
