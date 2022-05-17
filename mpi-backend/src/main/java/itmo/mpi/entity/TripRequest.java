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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "traveler", nullable = false)
    private User traveler;

    @Column(name = "date_start")
    private LocalDate dateStart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "island_start", nullable = false)
    private Island islandStart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "island_end", nullable = false)
    private Island islandEnd;

    @Column(name = "status", length = 20)
    private String status;

}