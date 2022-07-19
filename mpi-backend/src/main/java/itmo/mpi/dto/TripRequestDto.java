package itmo.mpi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import itmo.mpi.entity.Island;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Builder
@Setter
public class TripRequestDto {

    private Island from;
    private Island to;
    private long budget;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate startDate;

}
