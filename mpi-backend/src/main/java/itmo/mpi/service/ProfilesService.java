package itmo.mpi.service;

import itmo.mpi.entity.User;
import itmo.mpi.model.UserInfoUpdate;
import itmo.mpi.model.profiles.*;

import java.util.List;

public interface ProfilesService {

    CrewResponse registerCrew(String nickName, RegisterCrewRequest newCrew);

    CrewResponse updateCrew(String nickName, RegisterCrewRequest newCrew);

    ShipResponse registerShip(String nickName, ShipRequest newShip);

    ShipResponse updateShip(String nickName, ShipRequest newShipInfo);

    UserProfileResponse getCurrentUserProfile(String nickName);

    List<ShipProfileResponse> getShipsForCurrentUser(String nickname);

    List<CrewProfileResponse> getCrewsForCurrentUser(String nickname);

    CrewResponse getUserCrew(String nickname);

    ShipResponse getUserShip(String nickName);

    User updateUser(UserInfoUpdate userInfo);

    void changeVipStatus(boolean isEnabled);

    boolean getAfraid(String nickName);

    void updateShipAfraid(String nick, Boolean value);

    void updateCrewAfraid(String nick, Boolean value);
}
