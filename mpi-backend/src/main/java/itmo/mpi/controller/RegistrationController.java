package itmo.mpi.controller;

import itmo.mpi.service.AdminService;
import itmo.mpi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mpi/signup")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    private final AdminService adminService;



}
