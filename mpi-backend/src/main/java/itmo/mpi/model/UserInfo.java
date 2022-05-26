package itmo.mpi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfo {
    private String name;
    private String surname;
    private String nick;
    private String password;
    private String birthDate;
    private String userType;
    private String email;
    private String phone;

    public UserInfo(String name, String surname, String nick, String password, String birthDate, String userType, String email, String phone) {
        this.name = name;
        this.surname = surname;
        this.nick = nick;
        this.password = password;
        this.birthDate = birthDate;
        this.userType = userType;
        this.email = email;
        this.phone = phone;
    }
}
