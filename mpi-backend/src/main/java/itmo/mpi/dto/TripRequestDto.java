package itmo.mpi.dto;

import itmo.mpi.entity.Island;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Builder
public class TripRequestDto {

    private Island from;
    private Island to;
    private int budget;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

}