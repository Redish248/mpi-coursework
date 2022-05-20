package itmo.mpi.model.profiles;

import itmo.mpi.entity.Crew;
import itmo.mpi.entity.CrewMember;
import itmo.mpi.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Phone & email will be return only if user allows (else null)
 * Only VIP users get information about pirates
 */
public class CrewProfileResponse {
    private int uid;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Boolean isPirate;

    private String photo;
    private String description;

    private CrewResponse crew;

    @Data
    @AllArgsConstructor
    static class CrewResponse {
        private int uid;
        private String teamName;
        private int membersNumber;
        private List<CrewMember> members;
    }

    @Data
    @AllArgsConstructor
    static class CrewMember {
        private String name;
        private String surname;
    }

    public CrewProfileResponse(int uid, String name, String surname, String email, String phone, Boolean isPirate, String photo, String description, Crew crew, List<User> members) {
        this.uid = uid;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.isPirate = isPirate;
        this.photo = photo;
        this.description = description;

        this.crew = new CrewResponse(
                crew.getId(),
                crew.getTeamName(),
                members.size(),
                members.stream().map(el -> new CrewMember(el.getName(), el.getSurname())).collect(Collectors.toList())
        );
    }
}
