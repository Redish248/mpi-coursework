package itmo.mpi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "trip_request", schema = "s242361")
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
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateStart;

    @Column(name = "date_end")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonFormat(pattern = "dd.MM.yyyy")
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

    @Column(name = "is_rated")
    private Boolean isRated;

}