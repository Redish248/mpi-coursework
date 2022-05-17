package itmo.mpi.controller;

import itmo.mpi.entity.Admin;
import itmo.mpi.entity.User;
import itmo.mpi.service.AdminService;
import itmo.mpi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mpi/signup")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    private final AdminService adminService;

    @PostMapping( "/registerUser")
    public @ResponseBody ResponseEntity<User> registerUser(String name, String surname, String nick, String password, String birth_date, String user_type, String email, String phone) {
        return ResponseEntity.ok(userService.createUser(name, surname, nick, password, birth_date, user_type, email, phone));
    }

    @PostMapping( "/registerAdmin")
    public @ResponseBody ResponseEntity<Admin> registerAdmin(String name, String surname, String nick, String password, int salary) {
        return ResponseEntity.ok(adminService.createAdmin(name, surname, nick, password, salary));
    }

}
