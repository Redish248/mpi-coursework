package itmo.mpi.impl;

import itmo.mpi.entity.Admin;
import itmo.mpi.entity.User;
import itmo.mpi.exception.UserAlreadyExistException;
import itmo.mpi.model.ProfilesResponse;
import itmo.mpi.repository.*;
import itmo.mpi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final String TRAVELER = "TRAVELER";
    private final String SHIP_OWNER = "SHIP_OWNER";
    private final String CREW_MANAGER = "CREW_MANAGER";

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final AdminRepository adminRepository;

    @Override
    public List<User> findAllNotActivatedUsers() {
        return userRepository.findUsersByIsActivated(false);
    }

    @Override
    public User createUser(String name, String surname, String nick, String password, String birth_date, String user_type, String email, String phone) {
        User user = userRepository.findByNick(nick);
        Admin oldAdmin = adminRepository.findAdminByNick(nick);
        if (user != null || oldAdmin != null) {
            throw new UserAlreadyExistException(nick);
        }
        User newUser = new User();
        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setNick(nick);
        newUser.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(birth_date, formatter);
        newUser.setBirthDate(localDate);
        newUser.setUserType(userRoleRepository.findUserRoleByName(user_type));
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setIsActivated(user_type.equalsIgnoreCase(TRAVELER));
        newUser.setIsVip(false);
        newUser.setIsPirate(false);
        return userRepository.save(newUser);
    }

    @Override
    public List<ProfilesResponse> getShipsForCurrentUser(String nickname) {
        return getProfilesByTypeForUser(SHIP_OWNER, nickname);
    }

    @Override
    public List<ProfilesResponse> getCrewsForCurrentUser(String nickname) {
        return getProfilesByTypeForUser(CREW_MANAGER, nickname);
    }

    private List<ProfilesResponse> getProfilesByTypeForUser(String type, String nickname) {
        User currentUser = userRepository.findByNick(nickname);

        return userRepository
                .findAll().stream()
                .filter(el -> Objects.equals(el.getUserType().getName(), type))
                .map(el -> new ProfilesResponse(
                        el.getId(),
                        el.getName(),
                        el.getSurname(),
                        el.isShareContactInfo() ? el.getEmail() : null,
                        el.isShareContactInfo() ? el.getPhone() : null,
                        currentUser.getIsVip() ? el.getIsPirate() : null
                ))
                .collect(Collectors.toList());
    }
}
