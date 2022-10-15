package itmo.mpi.model.profiles;

import itmo.mpi.entity.Crew;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

/**
 * Phone & email will be return only if user allows (else null)
 * Only VIP users get information about pirates
 */
@Data
@NoArgsConstructor
public class CrewProfileResponse {
    // user profile info
    private int uid;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Boolean isPirate; // true false - for VIP & null for others

    // crew info
    private CrewResponse crew;

    public CrewProfileResponse(int uid, String name, String surname, String email, String phone,
                               Boolean isPirate, Crew crew, List<itmo.mpi.entity.CrewMember> members) {
        this.uid = uid;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.isPirate = isPirate;

        this.crew = new CrewResponse(
                crew,
                members
        );
    }
}
