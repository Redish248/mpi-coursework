package itmo.mpi.impl;

import itmo.mpi.entity.Admin;
import itmo.mpi.entity.User;
import itmo.mpi.exception.UserAlreadyExistException;
import itmo.mpi.model.UserInfo;
import itmo.mpi.repository.AdminRepository;
import itmo.mpi.repository.UserRepository;
import itmo.mpi.repository.UserRoleRepository;
import itmo.mpi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final AdminRepository adminRepository;

    @Override
    public List<UserInfo> findAllNotActivatedUsers() {
        List<User> allUsers = userRepository.findUsersByIsActivated(false);
        List<UserInfo> result = new ArrayList<>();
        allUsers.forEach(user -> {
            UserInfo userInfo = new UserInfo();
            userInfo.setName(user.getName());
            userInfo.setSurname(user.getSurname());
            userInfo.setNick(user.getNick());
            userInfo.setEmail(user.getEmail());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            userInfo.setBirthDate(user.getBirthDate().format(formatter));
            userInfo.setPhone(user.getPhone());
            userInfo.setUserType(user.getUserType().getName());
            result.add(userInfo);
        });
        return result;
    }

    @Override
    public User createUser(UserInfo requestUser) {
        User user = userRepository.findByNick(requestUser.getNick());
        Admin oldAdmin = adminRepository.findAdminByNick(requestUser.getNick());
        if (user != null || oldAdmin != null) {
            throw new UserAlreadyExistException(requestUser.getNick());
        }
        User newUser = new User();
        newUser.setName(requestUser.getName());
        newUser.setSurname(requestUser.getSurname());
        newUser.setNick(requestUser.getNick());
        newUser.setPassword(BCrypt.hashpw(requestUser.getPassword(), BCrypt.gensalt()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(requestUser.getBirthDate(), formatter);
        newUser.setBirthDate(localDate);
        newUser.setUserType(userRoleRepository.findUserRoleByName(requestUser.getUserType().toUpperCase(Locale.ROOT)));
        newUser.setEmail(requestUser.getEmail());
        newUser.setPhone(requestUser.getPhone());
        newUser.setIsActivated(requestUser.getUserType().equalsIgnoreCase("traveler"));
        newUser.setIsVip(false);
        newUser.setIsPirate(false);
        return userRepository.save(newUser);
    }
}
