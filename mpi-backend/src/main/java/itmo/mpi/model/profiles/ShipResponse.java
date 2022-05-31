package itmo.mpi.model.profiles;

import itmo.mpi.entity.Ship;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShipResponse {
    private int uid;
    private String title;
    private int speed;
    private int capacity;
    private int fuelConsumption;
    private int length;
    private int width;
    private int pricePerDay;
    private String photo;
    private String description;

    public ShipResponse(Ship ship) {
        this.uid = ship.getId();
        this.title = ship.getName();
        this.speed = ship.getSpeed();
        this.capacity = ship.getCapacity();
        this.fuelConsumption = ship.getFuelConsumption();
        this.width = ship.getWidth();
        this.length = ship.getLength();
        this.pricePerDay = ship.getPricePerDay();
        this.photo = ship.getPhoto();
        this.description = ship.getDescription();
    }
}
