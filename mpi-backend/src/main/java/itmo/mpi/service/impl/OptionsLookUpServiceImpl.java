package itmo.mpi.service.impl;

import itmo.mpi.dto.TripOption;
import itmo.mpi.dto.TripRequestDto;
import itmo.mpi.dto.ShipOption;
import itmo.mpi.entity.Crew;
import itmo.mpi.entity.Island;
import itmo.mpi.entity.Ship;
import itmo.mpi.repository.CrewRepository;
import itmo.mpi.repository.ShipRepository;
import itmo.mpi.service.OptionsLookUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OptionsLookUpServiceImpl implements OptionsLookUpService {

    private final CrewRepository crewRepository;
    private final ShipRepository shipRepository;

    @Override
    public List<TripOption> lookUpOptions(TripRequestDto tripRequestDto) {
        List<Crew> crews = crewRepository.findAll();
        List<ShipOption> shipOptions = getShipOptions(tripRequestDto);

        return shipOptions.stream()
                .flatMap(shipOption -> {
                    int days = shipOption.getDays();
                    Ship ship = shipOption.getShip();
                    int shipCost = (ship.getFuelConsumption()+ship.getPricePerDay()) * days;

                    return crews.stream()
                            .filter(crew -> crew.getPricePerDay()*days + shipCost <= tripRequestDto.getBudget())
                            .map(crew -> TripOption.builder()
                                    .crew(crew)
                                    .ship(ship)
                                    .price(crew.getPricePerDay()*days + shipCost)
                                    .startDate(tripRequestDto.getStartDate())
                                    .finishDate(tripRequestDto.getStartDate().plus(days, ChronoUnit.DAYS))
                                    .build());
                })
                .collect(Collectors.toList());
    }

    private List<ShipOption> getShipOptions(TripRequestDto tripRequestDto) {
        return shipRepository.findAll().stream()
                .map(ship -> ShipOption.builder()
                        .ship(ship)
                        .days(calculateDistance(tripRequestDto.getFrom(), tripRequestDto.getTo()) /
                        ship.getSpeed() + 1)
                        .build())
                .collect(Collectors.toList());
    }

    int calculateDistance(Island from, Island to) {
        return (int) Math.ceil(Math.sqrt(Math.pow(from.getXCoordinate() - to.getXCoordinate(), 2) +
                Math.pow(from.getYCoordinate() - to.getYCoordinate(), 2)));
    }
}
