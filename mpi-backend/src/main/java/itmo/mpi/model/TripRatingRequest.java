package itmo.mpi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TripRatingRequest {
    private Integer tripId;
    private Double ship;
    private Double crew;
}
