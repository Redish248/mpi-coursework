package itmo.mpi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "users", schema = "s242361")
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

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getNick() {
        return nick;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public boolean isShareContactInfo() {
        return shareContactInfo;
    }

    public UserRole getUserType() {
        return userType;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Boolean getIsPirate() {
        return isPirate;
    }

    public Boolean getIsActivated() {
        return isActivated;
    }

    public Boolean getIsVip() {
        return isVip;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setShareContactInfo(boolean shareContactInfo) {
        this.shareContactInfo = shareContactInfo;
    }

    public void setUserType(UserRole userType) {
        this.userType = userType;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setIsPirate(Boolean pirate) {
        isPirate = pirate;
    }

    public void setIsActivated(Boolean activated) {
        isActivated = activated;
    }

    public void setIsVip(Boolean vip) {
        isVip = vip;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}