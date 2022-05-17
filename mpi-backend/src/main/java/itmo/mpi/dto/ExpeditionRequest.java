package itmo.mpi.dto;

import itmo.mpi.entity.Island;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExpeditionRequest {

    private Island from;
    private Island to;
    private int budget;

}
