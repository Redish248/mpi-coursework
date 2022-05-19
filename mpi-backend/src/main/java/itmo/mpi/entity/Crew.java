package itmo.mpi.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "crew")
@Data
public class Crew {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    private Integer id;

    @Column(name = "team_name", length = 50)
    private String teamName;

    @ManyToOne(optional = false)
    @JoinColumn(name = "crew_owner", nullable = false)
    private User crewOwner;

    @Column(name = "price_per_day", nullable = false)
    private Integer pricePerDay;

    @Column(name = "rates_number", nullable = false)
    private Integer ratesNumber;

    @Column(name = "rates_average", nullable = false)
    private Double ratesAverage;

}