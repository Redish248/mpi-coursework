package itmo.mpi.model;

import lombok.Data;

/**
 * Phone & email will be return only if user allows
 * Only VIP users get information about pirates
 */
@Data
public class ProfilesResponse {
    int uid;
    String name;
    String surname;
    String email;
    String phone;
    Boolean isPirate;


    public ProfilesResponse(int uid, String name, String surname, String email, String phone, Boolean isPirate) {
        this.uid = uid;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.isPirate = isPirate;
    }
}
