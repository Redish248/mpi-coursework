package itmo.mpi.model.profiles;

import lombok.Data;

@Data
public class RegisterCrewMemberRequest {
    private String fullName;
    private int experience;
}
