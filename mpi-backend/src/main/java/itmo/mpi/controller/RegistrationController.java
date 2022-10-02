package itmo.mpi.controller;

import itmo.mpi.entity.User;
import itmo.mpi.model.UserInfo;
import itmo.mpi.service.UserService;
import itmo.mpi.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mpi/signup")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    private final CommonUtils commonUtils;

    @PostMapping("/registerUser")
    public @ResponseBody
    ResponseEntity<User> registerUser(@RequestBody UserInfo newUser) {
        return ResponseEntity.ok(userService.createUser(newUser));
    }

    @GetMapping("/roles")
    public @ResponseBody
    String getPermissions() {
        return commonUtils.getCurrentUser().getAuthorities().stream().findFirst().get().getAuthority();
    }
}
