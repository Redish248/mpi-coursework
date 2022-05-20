package itmo.mpi.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

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

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

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

}