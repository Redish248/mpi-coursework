package itmo.mpi.model.profiles;

import itmo.mpi.entity.Crew;
import itmo.mpi.entity.CrewMember;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrewResponse {
    private int uid;
    private String teamName;
    private double rates;
    private String photo;
    private String description;
    private int pricePerDay;
    private int membersNumber;
    private List<CrewMemberResponse> members;

    @Data
    @AllArgsConstructor
    public static class CrewMemberResponse {
        private int uid;
        private String fullName;
        private int experience;
    }

    public CrewResponse(Crew crew, List<CrewMember> members) {
        this.uid = crew.getId();
        this.teamName = crew.getTeamName();
        this.rates = crew.getRatesAverage();
        this.photo = crew.getPhoto();
        this.description = crew.getDescription();
        this.pricePerDay = crew.getPricePerDay();
        this.membersNumber = members.size();
        this.members = members.stream().map(el -> new CrewMemberResponse(el.getId(), el.getFullName(), el.getExperience())).collect(Collectors.toList());
    }
}