package itmo.mpi.model.profiles;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserProfileResponse {
    private int uid;
    private String name;
    private String surname;
    private String nick;
    private String birthDate;
    private String email;
    private String phone;
    private boolean shareContactInfo;
    private boolean isVip;
    private boolean isActivated;
}
