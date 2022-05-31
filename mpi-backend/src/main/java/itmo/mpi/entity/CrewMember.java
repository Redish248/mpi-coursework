package itmo.mpi.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "crew_member")
@Data
public class CrewMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "crew", nullable = false)
    private Crew crew;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "experience")
    private int experience;

    public CrewMember(Crew crew, String fullName, int experience) {
        this.crew = crew;
        this.fullName = fullName;
        this.experience = experience;
    }

    public CrewMember() {

    }
}