package itmo.mpi.model.profiles;

import lombok.Data;

@Data
public class ShipRequest {
    private String name;
    private int speed;
    private int capacity;
    private int fuelConsumption;
    private int length;
    private int width;
    private int pricePerDay;
    private String photo;
    private String description;
}
