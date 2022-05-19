package itmo.mpi.model;

import lombok.Data;

@Data
public class UserInfo {
    private String name;
    private String surname;
    private String nick;
    private String password;
    private String birthDate;
    private String userType;
    private String email;
    private String phone;
}
