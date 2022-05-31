package itmo.mpi.model.profiles;

import itmo.mpi.entity.Crew;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CrewResponse {
    private int uid;
    private String teamName;
    private double rates;
    private String photo;
    private String description;
    private int pricePerDay;
    private int membersNumber;
    private List<CrewMember> members;

    @Data
    @AllArgsConstructor
    static class CrewMember {
        private int uid;
        private String fullName;
        private int experience;
    }

    public CrewResponse(Crew crew, List<itmo.mpi.entity.CrewMember> members) {
        this.uid = crew.getId();
        this.teamName = crew.getTeamName();
        this.rates = crew.getRatesAverage();
        this.photo = crew.getPhoto();
        this.description = crew.getDescription();
        this.pricePerDay = crew.getPricePerDay();
        this.membersNumber = members.size();
        this.members = members.stream().map(el -> new CrewMember(el.getId(), el.getFullName(), el.getExperience())).collect(Collectors.toList());
    }
}