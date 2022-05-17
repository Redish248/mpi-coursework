package itmo.mpi.controller;

import itmo.mpi.entity.User;
import itmo.mpi.service.AdminService;
import itmo.mpi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mpi/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    private final UserService userService;

    @GetMapping( "/notActivatedUsers")
    public @ResponseBody ResponseEntity<List<User>> getAllNotActivatedUsers() {
        List<User> result = userService.findAllNotActivatedUsers();
        return ResponseEntity.ok().body(result);
    }

    @PostMapping( "/processUser")
    public void processUser(String nick, boolean isActivated) {
        adminService.processUser(nick, isActivated);
    }

}
