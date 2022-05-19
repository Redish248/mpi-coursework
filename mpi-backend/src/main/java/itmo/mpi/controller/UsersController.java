package itmo.mpi.controller;

import itmo.mpi.model.ProfilesResponse;
import itmo.mpi.service.UserService;
import itmo.mpi.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mpi/profiles")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    private final CommonUtils commonUtils;

    @GetMapping("ships")
    public List<ProfilesResponse> getShipsProfiles() {
        String nickname = commonUtils.getCurrentUser().getName();
        return userService.getShipsForCurrentUser(nickname);
    }

    @GetMapping("crews")
    public List<ProfilesResponse> getCrewsProfiles() {
        String nickname = commonUtils.getCurrentUser().getName();
        return userService.getCrewsForCurrentUser(nickname);
    }
}
