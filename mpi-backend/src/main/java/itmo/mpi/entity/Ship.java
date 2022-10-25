package itmo.mpi.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ship")
@Data
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner", nullable = false)
    private User owner;

    @Column(name = "speed")
    private Integer speed;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "fuel_consumption")
    private Integer fuelConsumption;

    @Column(name = "length")
    private Integer length;

    @Column(name = "width")
    private Integer width;

    @Column(name = "price_per_day", nullable = false)
    private Integer pricePerDay;

    @Column(name = "rates_number", nullable = false)
    private Integer ratesNumber;

    @Column(name = "rates_average", nullable = false)
    private Double ratesAverage;

    @Column(name = "photo")
    private String photo;

    @Column(name = "description")
    private String description;

    public Boolean getAfraidPirates() {
        return afraidPirates;
    }

    public void setAfraidPirates(Boolean afraidPirates) {
        this.afraidPirates = afraidPirates;
    }

    @Column(name = "afraid_pirates")
    private Boolean afraidPirates;

    public Ship(String name, User owner, Integer speed, Integer capacity, Integer fuelConsumption, Integer length, Integer width, Integer pricePerDay, String photo, String description) {
        this.name = name;
        this.owner = owner;
        this.speed = speed;
        this.capacity = capacity;
        this.fuelConsumption = fuelConsumption;
        this.length = length;
        this.width = width;
        this.pricePerDay = pricePerDay;
        this.ratesNumber = 0;
        this.ratesAverage = 0.0;
        this.photo = photo;
        this.description = description;
    }

    public Ship() {
    }
}