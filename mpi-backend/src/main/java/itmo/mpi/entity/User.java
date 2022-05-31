package itmo.mpi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @Column(name = "surname", nullable = false, length = 40)
    private String surname;

    @Column(name = "nick", length = 50)
    private String nick;

    @Column(name = "password", nullable = false, length = 100)
    @JsonIgnore
    private String password;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "share_contacts")
    private boolean shareContactInfo;

    @ManyToOne
    @JoinColumn(name = "user_type", nullable = false)
    private UserRole userType;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "is_pirate")
    private Boolean isPirate;

    @Column(name = "is_activated")
    private Boolean isActivated;

    @Column(name = "is_vip")
    private Boolean isVip;

}