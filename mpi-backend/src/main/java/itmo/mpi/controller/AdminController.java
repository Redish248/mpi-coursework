package itmo.mpi.controller;

import itmo.mpi.entity.Admin;
import itmo.mpi.model.NewFsbAgentRequest;
import itmo.mpi.model.UserInfo;
import itmo.mpi.service.AdminService;
import itmo.mpi.service.FsbAgentService;
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

    private final FsbAgentService fsbAgentService;

    @GetMapping("/notActivatedUsers")
    public @ResponseBody
    ResponseEntity<List<UserInfo>> getAllNotActivatedUsers() {
        List<UserInfo> result = userService.findAllNotActivatedUsers();
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/processUser")
    public HttpStatus processUser(String nick, boolean isActivated) {
        adminService.processUser(nick, isActivated);
        return HttpStatus.OK;
    }

    @PostMapping("/registerAdmin")
    public @ResponseBody
    ResponseEntity<Admin> registerAdmin(String name, String surname, String nick, String password, int salary) {
        return ResponseEntity.ok(adminService.createAdmin(name, surname, nick, password, salary));
    }

    @PostMapping("/registerFsb")
    public @ResponseBody
    ResponseEntity registerFsbAgent(NewFsbAgentRequest request) {
        fsbAgentService.registerNewAgent(request);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
