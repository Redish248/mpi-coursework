package itmo.mpi.model.profiles;

import lombok.Data;

import java.util.List;

@Data
public class CrewRequest {
    private String teamName;
    private String photo;
    private String description;
    private int pricePerDay;
    private List<CrewMember> members;
}
