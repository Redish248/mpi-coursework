package itmo.mpi.controller;

import itmo.mpi.entity.Ship;
import itmo.mpi.entity.User;
import itmo.mpi.model.UserInfoUpdate;
import itmo.mpi.model.profiles.*;
import itmo.mpi.service.ProfilesService;
import itmo.mpi.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mpi/profiles")
@RequiredArgsConstructor
public class ProfilesController {

    private final ProfilesService profilesService;

    private final CommonUtils commonUtils;

    @GetMapping
    public UserProfileResponse getCurrentUserProfile() {
        String nickname = commonUtils.getCurrentUser().getName();
        return profilesService.getCurrentUserProfile(nickname);
    }

    @GetMapping("ship")
    public ShipResponse getUserShip() {
        String nickname = commonUtils.getCurrentUser().getName();
        return profilesService.getUserShip(nickname);
    }

    @GetMapping("/crew")
    public CrewResponse getCurrentUserCrew() {
        String nickname = commonUtils.getCurrentUser().getName();
        return profilesService.getUserCrew(nickname);
    }

    @GetMapping("ships")
    public List<ShipProfileResponse> getShipsProfiles() {
        String nickname = commonUtils.getCurrentUser().getName();
        return profilesService.getShipsForCurrentUser(nickname);
    }

    @GetMapping("/crews")
    public List<CrewProfileResponse> getCrewsProfiles() {
        String nickname = commonUtils.getCurrentUser().getName();
        return profilesService.getCrewsForCurrentUser(nickname);
    }

    @PostMapping("/ship")
    public ShipResponse registerShip(@RequestBody ShipRequest request) {
        String nickname = commonUtils.getCurrentUser().getName();
        return profilesService.registerShip(nickname, request);
    }

    @PostMapping("/crew")
    public CrewResponse registerCrew(@RequestBody CrewRequest request) {
        String nickname = commonUtils.getCurrentUser().getName();
        return profilesService.registerCrew(nickname, request);
    }

    @PostMapping("/shipinfo")
    public ShipResponse updateShip(@RequestBody ShipRequest request) {
        String nickname = commonUtils.getCurrentUser().getName();
        return profilesService.updateShip(nickname, request);
    }

    @PostMapping("/userinfo")
    public User updateUserInfo(@RequestBody UserInfoUpdate request) {
        return profilesService.updateUser(request);
    }
}
