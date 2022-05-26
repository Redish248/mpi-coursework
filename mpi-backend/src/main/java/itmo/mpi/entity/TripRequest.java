package itmo.mpi.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "trip_request")
@Data
public class TripRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "traveler", nullable = false)
    private User traveler;

    @Column(name = "date_start")
    private LocalDate dateStart;

    @Column(name = "date_end")
    private LocalDate dateEnd;

    @ManyToOne(optional = false)
    @JoinColumn(name = "island_start", nullable = false)
    private Island islandStart;

    @ManyToOne(optional = false)
    @JoinColumn(name = "island_end", nullable = false)
    private Island islandEnd;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private TripRequestStatus status;

    @ManyToOne
    @JoinColumn(name = "ship_id")
    private Ship ship;

    @ManyToOne
    @JoinColumn(name = "crew_id")
    private Crew crew;

    @Column(name = "cost")
    private Integer cost;

}