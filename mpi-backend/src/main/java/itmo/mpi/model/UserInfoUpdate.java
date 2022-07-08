package itmo.mpi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoUpdate {
    private String name;
    private String surname;
    private String birthDate;
    private boolean shareContacts;
    private String email;
    private String phone;
}
