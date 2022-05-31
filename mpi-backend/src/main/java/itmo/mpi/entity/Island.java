package itmo.mpi.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "island")
@Data
public class Island {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @Column(name = "x_coordinate", nullable = false)
    private Integer xCoordinate;

    @Column(name = "y_coordinate", nullable = false)
    private Integer yCoordinate;

    @Column(name = "has_pirates")
    private Boolean hasPirates;

}