package itmo.mpi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admin", schema = "s242361")
@Data
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @Column(name = "surname", nullable = false, length = 40)
    private String surname;

    @Column(name = "salary")
    private Integer salary;

    @Column(name = "nick", length = 50)
    private String nick;

    @Column(name = "password", nullable = false, length = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}