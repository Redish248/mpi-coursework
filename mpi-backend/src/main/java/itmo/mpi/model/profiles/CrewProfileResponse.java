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
    // user profile info
    private int uid;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Boolean isPirate; // true false - for VIP & null for others

    // crew info
    private CrewResponse crew;

    @Data
    @AllArgsConstructor
    static class CrewResponse {
        private int uid;
        private String teamName;
        private int rates;
        private String photo;
        private String description;
        private int pricePerDay;
        private int membersNumber;
        private List<CrewMember> members;
    }

    @Data
    @AllArgsConstructor
    static class CrewMember {
        private int uid;
        private String fullName;
        private int experience;
    }

    public CrewProfileResponse(int uid, String name, String surname, String email, String phone, Boolean isPirate, Crew crew, List<itmo.mpi.entity.CrewMember> members) {
        this.uid = uid;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.isPirate = isPirate;

        this.crew = new CrewResponse(
                crew.getId(),
                crew.getTeamName(),
                crew.getRatesNumber(),
                crew.getPhoto(),
                crew.getDescription(),
                crew.getPricePerDay(),
                members.size(),
                members.stream().map(el -> new CrewMember(el.getId(), el.getFullName(), el.getExperience())).collect(Collectors.toList())
        );
    }
}
