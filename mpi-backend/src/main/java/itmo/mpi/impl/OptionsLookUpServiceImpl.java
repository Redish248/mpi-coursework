package itmo.mpi.impl;

import itmo.mpi.dto.ShipOption;
import itmo.mpi.dto.TripOption;
import itmo.mpi.dto.TripRequestDto;
import itmo.mpi.entity.Crew;
import itmo.mpi.entity.Island;
import itmo.mpi.entity.Ship;
import itmo.mpi.repository.CrewRepository;
import itmo.mpi.repository.IslandRepository;
import itmo.mpi.repository.ShipRepository;
import itmo.mpi.service.OptionsLookUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OptionsLookUpServiceImpl implements OptionsLookUpService {

    private final CrewRepository crewRepository;
    private final ShipRepository shipRepository;
    private final IslandRepository islandRepository;

    @Override
    public List<TripOption> lookUpOptions(TripRequestDto tripRequestDto) {
        preprocessRequest(tripRequestDto);
        List<ShipOption> shipOptions = getShipOptions(tripRequestDto);

        return shipOptions.stream()
                .filter(ship -> ship.getShip().getOwner().getIsActivated())
                .flatMap(shipOption -> {
                    int days = shipOption.getDays();
                    Ship ship = shipOption.getShip();
                    int shipCost = (ship.getFuelConsumption() + ship.getPricePerDay()) * days;
                    LocalDate finishDate = tripRequestDto.getStartDate().plus(days, ChronoUnit.DAYS);

                    List<Crew> crewsForShip = crewRepository.getFreeCrewsForDates(tripRequestDto.getStartDate(),
                            finishDate);

                    return crewsForShip.stream()
                            .filter(crew -> crew.getCrewOwner().getIsActivated() &&
                                    (long) crew.getPricePerDay() * days + shipCost <= tripRequestDto.getBudget())
                            .map(crew -> TripOption.builder()
                                    .crew(crew)
                                    .ship(ship)
                                    .price(crew.getPricePerDay() * days + shipCost)
                                    .startDate(tripRequestDto.getStartDate())
                                    .finishDate(tripRequestDto.getStartDate().plus(days, ChronoUnit.DAYS))
                                    .build());
                })
                .filter(candidate -> {
                    var dangerous = tripRequestDto.getFrom().getHasPirates() ||
                            tripRequestDto.getTo().getHasPirates() ||
                            candidate.getCrew().getCrewOwner().getIsPirate();
                    var afraid = candidate.getCrew().getAfraidPirates() || candidate.getShip().getAfraidPirates();

                    return !(dangerous && afraid);
                })
                .collect(Collectors.toList());
    }

    private List<ShipOption> getShipOptions(TripRequestDto tripRequestDto) {
        int distance = calculateDistance(tripRequestDto.getFrom(), tripRequestDto.getTo());
        return shipRepository.getFreeShipsForTrip(tripRequestDto.getStartDate(), distance)
                .stream()
                .filter(ship -> (long) (travelDurationInDays(distance, ship.getSpeed()))
                        * (ship.getPricePerDay() + ship.getFuelConsumption()) <= tripRequestDto.getBudget())
                .map(ship -> ShipOption.builder()
                        .ship(ship)
                        .days(travelDurationInDays(distance, ship.getSpeed()))
                        .build())
                .collect(Collectors.toList());
    }

    private int travelDurationInDays(int distance, int speed) {
        return (int) Math.ceil((float) distance / speed);
    }

    private int calculateDistance(Island from, Island to) {
        return (int) Math.ceil(Math.sqrt(Math.pow(from.getXCoordinate() - to.getXCoordinate(), 2) +
                Math.pow(from.getYCoordinate() - to.getYCoordinate(), 2)));
    }

    private void preprocessRequest(TripRequestDto requestDto) {
        if (requestDto.getFrom().getYCoordinate() == null || requestDto.getFrom().getXCoordinate() == null) {
            requestDto.setFrom(islandRepository.getById(requestDto.getFrom().getId()));
        }
        if (requestDto.getTo().getYCoordinate() == null || requestDto.getTo().getXCoordinate() == null) {
            requestDto.setTo(islandRepository.getById(requestDto.getTo().getId()));
        }
    }

}
