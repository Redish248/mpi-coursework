package itmo.mpi.dto;

import itmo.mpi.entity.Crew;
import itmo.mpi.entity.Ship;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExpeditionOption {

    private Ship ship;
    private Crew crew;
    private int price;

}
