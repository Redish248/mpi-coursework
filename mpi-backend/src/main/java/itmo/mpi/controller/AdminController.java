package itmo.mpi.controller;

import itmo.mpi.model.UserInfo;
import itmo.mpi.service.AdminService;
import itmo.mpi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mpi/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    private final UserService userService;

    @GetMapping( "/notActivatedUsers")
    public @ResponseBody ResponseEntity<List<UserInfo>> getAllNotActivatedUsers() {
        List<UserInfo> result = userService.findAllNotActivatedUsers();
        return ResponseEntity.ok().body(result);
    }

    @PostMapping( "/processUser")
    public HttpStatus processUser(String nick, boolean isActivated) {
        adminService.processUser(nick, isActivated);
        return HttpStatus.OK;
    }

}
