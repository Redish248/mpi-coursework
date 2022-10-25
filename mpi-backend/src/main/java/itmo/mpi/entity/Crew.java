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
@Table(name = "crew", schema = "s242361")
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

    @Column(name = "photo")
    private String photo;

    @Column(name = "description")
    private String description;

    @Column(name = "afraid_pirates")
    private Boolean afraidPirates;

    public Crew(String teamName, User crewOwner, Integer pricePerDay, String photo, String description) {
        this.teamName = teamName;
        this.crewOwner = crewOwner;
        this.pricePerDay = pricePerDay;
        this.photo = photo;
        this.description = description;
        this.ratesAverage = 0.0;
        this.ratesNumber = 0;
    }

    public Crew() {

    }

    public Integer getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public User getCrewOwner() {
        return crewOwner;
    }

    public Integer getPricePerDay() {
        return pricePerDay;
    }

    public Integer getRatesNumber() {
        return ratesNumber;
    }

    public Double getRatesAverage() {
        return ratesAverage;
    }

    public String getPhoto() {
        return photo;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setCrewOwner(User crewOwner) {
        this.crewOwner = crewOwner;
    }

    public void setPricePerDay(Integer pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public void setRatesNumber(Integer ratesNumber) {
        this.ratesNumber = ratesNumber;
    }

    public void setRatesAverage(Double ratesAverage) {
        this.ratesAverage = ratesAverage;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAfraidPirates() {
        return afraidPirates;
    }

    public void setAfraidPirates(Boolean afraidPirates) {
        this.afraidPirates = afraidPirates;
    }
}