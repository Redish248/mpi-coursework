package itmo.mpi.service.impl;

import itmo.mpi.entity.*;
import itmo.mpi.repository.TripRequestRepository;
import itmo.mpi.repository.UserRepository;
import itmo.mpi.service.TripRequestInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static itmo.mpi.entity.TripRequestStatus.*;
import static itmo.mpi.util.Const.TRAVELLER_ROLE;

@RequiredArgsConstructor
@Service
public class TripRequestInfoServiceImpl implements TripRequestInfoService {

    private final TripRequestRepository requestRepository;
    private final UserRepository userRepository;

    @Override
    public List<TripRequest> getPendingRequestsForShip(Ship ship) {
        return requestRepository.findByShipAndStatusIn(ship, getPendingStatuses());
    }

    @Override
    public List<TripRequest> getPendingRequestsForCrew(Crew crew) {
        return requestRepository.findByCrewAndStatusIn(crew, getPendingStatuses());
    }

    @Override
    public List<TripRequest> getCompleteRequestsForShip(Ship ship) {
        return requestRepository.findByShipAndStatusIn(ship, getCompleteStatuses());
    }

    @Override
    public List<TripRequest> getCompleteRequestsForCrew(Crew crew) {
        return requestRepository.findByCrewAndStatusIn(crew, getCompleteStatuses());
    }

    @Override
    public List<TripRequest> getPendingRequestsForTraveller(String username) {
        return getRequestsForUserWithStatuses(username, getPendingStatuses());
    }

    @Override
    public List<TripRequest> getCompleteRequestsForTraveller(String username) {
        return getRequestsForUserWithStatuses(username, getCompleteStatuses());
    }

    @Override
    public List<TripRequest> getApprovedRequestsForShip(Ship ship) {
        return requestRepository.findByShipAndStatusIn(ship, getApprovedShipStatuses());
    }

    @Override
    public List<TripRequest> getApprovedRequestsForCrew(Crew crew) {
        return requestRepository.findByCrewAndStatusIn(crew, getApprovedCrewStatuses());
    }

    private List<TripRequest> getRequestsForUserWithStatuses(String username, List<TripRequestStatus> statuses) {
        User user = userRepository.findByNick(username);
        if (user.getUserType().getName().equals(TRAVELLER_ROLE)) {
            return requestRepository.findByTravellerAndStatusIn(user, statuses);
        } else {
            throw new IllegalArgumentException(String.format("User %s is not a traveller", username));
        }
    }

    private List<TripRequestStatus> getPendingStatuses() {
        return Stream.of(PENDING, APPROVED_BY_SHIP, APPROVED_BY_CREW, APPROVED_BY_CREW_AND_SHIP)
                .collect(Collectors.toList());
    }

    private List<TripRequestStatus> getCompleteStatuses() {
        return Stream.of(COMPLETE).collect(Collectors.toList());
    }

    private List<TripRequestStatus> getApprovedShipStatuses() {
        return Stream.of(COMPLETE, APPROVED_BY_SHIP, APPROVED_BY_CREW_AND_SHIP)
                .collect(Collectors.toList());
    }

    private List<TripRequestStatus> getApprovedCrewStatuses() {
        return Stream.of(COMPLETE, APPROVED_BY_CREW, APPROVED_BY_CREW_AND_SHIP)
                .collect(Collectors.toList());
    }

}
