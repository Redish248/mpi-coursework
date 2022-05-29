package itmo.mpi.model.profiles;

import java.util.List;

public class CrewRequest {
    private String teamName;
    private String photo;
    private String description;
    private int pricePerDay;
    private List<CrewMember> members;

    static class CrewMember {
        private String fullName;
        private int experience;
    }
}
