package itmo.mpi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate finishDate;

}
