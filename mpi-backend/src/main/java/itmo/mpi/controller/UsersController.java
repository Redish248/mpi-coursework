package itmo.mpi.controller;

import itmo.mpi.model.profiles.CrewProfileResponse;
import itmo.mpi.model.profiles.ShipProfileResponse;
import itmo.mpi.service.ProfilesService;
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

    private final ProfilesService profilesService;

    private final CommonUtils commonUtils;

    @GetMapping("ships")
    public List<ShipProfileResponse> getShipsProfiles() {
        String nickname = commonUtils.getCurrentUser().getName();
        return profilesService.getShipsForCurrentUser(nickname);
    }

    @GetMapping("crews")
    public List<CrewProfileResponse> getCrewsProfiles() {
        String nickname = commonUtils.getCurrentUser().getName();
        return profilesService.getCrewsForCurrentUser(nickname);
    }
}
