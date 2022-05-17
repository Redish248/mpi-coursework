package itmo.mpi.dto;

import itmo.mpi.entity.Crew;
import itmo.mpi.entity.Ship;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Builder
public class TripOption {

    private Ship ship;
    private Crew crew;
    private int price;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finishDate;

}
