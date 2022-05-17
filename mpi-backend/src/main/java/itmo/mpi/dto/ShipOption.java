package itmo.mpi.dto;

import itmo.mpi.entity.Ship;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShipOption {

    private Ship ship;
    private int days;

}
