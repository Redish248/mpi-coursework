package itmo.mpi.model.profiles;

import itmo.mpi.entity.Ship;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShipProfileResponse {
    private int uid;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Boolean isPirate;
    private ShipResponse ship;

    public ShipProfileResponse(int uid, String name, String surname, String email, String phone, Boolean isPirate, Ship ship) {
        this.uid = uid;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.isPirate = isPirate;

        this.ship = new ShipResponse(ship);
    }
}
