package itmo.mpi.model.profiles;

import itmo.mpi.entity.Ship;
import lombok.AllArgsConstructor;
import lombok.Data;

public class ShipProfileResponse {
    private int uid;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Boolean isPirate;

    private ShipResponse ship;

    @Data
    @AllArgsConstructor
    static class ShipResponse {
        private int uid;
        private String title;
        private int speed;
        private int capacity;
        private String photo;
        private String description;
    }

    public ShipProfileResponse(int uid, String name, String surname, String email, String phone, Boolean isPirate, Ship ship) {
        this.uid = uid;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.isPirate = isPirate;

        this.ship = new ShipResponse(
                ship.getId(),
                ship.getName(),
                ship.getSpeed(),
                ship.getCapacity(),
                ship.getPhoto(),
                ship.getDescription()
        );
    }
}
