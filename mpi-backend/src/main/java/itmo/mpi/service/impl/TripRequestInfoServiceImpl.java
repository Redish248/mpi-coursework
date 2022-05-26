package itmo.mpi.service.impl;

import itmo.mpi.entity.*;
import itmo.mpi.repository.CrewRepository;
import itmo.mpi.repository.ShipRepository;
import itmo.mpi.repository.TripRequestRepository;
import itmo.mpi.repository.UserRepository;
import itmo.mpi.service.TripRequestInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static itmo.mpi.entity.TripRequestStatus.*;
import static itmo.mpi.util.Const.*;

@RequiredArgsConstructor
@Service
public class TripRequestInfoServiceImpl implements TripRequestInfoService {

    private final TripRequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ShipRepository shipRepository;
    private final CrewRepository crewRepository;

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
    public List<TripRequest> getPendingRequestsForUser(String username) {
        User user = userRepository.findByNick(username);
        switch (user.getUserType().getName()) {
            case TRAVELLER_ROLE:
                return getRequestsForUserWithStatuses(user, getPendingStatuses());
            case CREW_ROLE:
                return getPendingRequestsForCrewOwner(user);
            case SHIP_ROLE:
                return getPendingRequestsForShipOwner(user);
            default:
                throw new IllegalArgumentException("User has no role");
        }
    }

    @Override
    public List<TripRequest> getCompleteRequestsForUser(String username) {
        User user = userRepository.findByNick(username);
        switch (user.getUserType().getName()) {
            case TRAVELLER_ROLE:
                return getRequestsForUserWithStatuses(user, getCompleteStatuses());
            case CREW_ROLE:
                return getCompleteRequestsForCrewOwner(user);
            case SHIP_ROLE:
                return getCompleteRequestsForShipOwner(user);
            default:
                throw new IllegalArgumentException("User has no role");
        }
    }

    @Override
    public List<TripRequest> getCancelledRequestsForUser(String username) {
        User user = userRepository.findByNick(username);
        switch (user.getUserType().getName()) {
            case TRAVELLER_ROLE:
                return getRequestsForUserWithStatuses(user, getCancelledStatuses());
            case CREW_ROLE:
                return getCancelledRequestsForCrewOwner(user);
            case SHIP_ROLE:
                return getCancelledRequestsForShipOwner(user);
            default:
                throw new IllegalArgumentException("User has no role");
        }
    }

    @Override
    public List<TripRequest> getApprovedRequestsForShip(Ship ship) {
        return requestRepository.findByShipAndStatusIn(ship, getApprovedShipStatuses());
    }

    @Override
    public List<TripRequest> getApprovedRequestsForCrew(Crew crew) {
        return requestRepository.findByCrewAndStatusIn(crew, getApprovedCrewStatuses());
    }

    private List<TripRequest> getPendingRequestsForCrewOwner(User crewOwner) {
        List<Crew> crews = crewRepository.findByCrewOwner(crewOwner);
        return requestRepository.findByCrewInAndStatusIn(crews, getPendingStatuses());
    }

    private List<TripRequest> getPendingRequestsForShipOwner(User shipOwner) {
        List<Ship> ships = shipRepository.findByOwner(shipOwner);
        return requestRepository.findByShipInAndStatusIn(ships, getPendingStatuses());
    }

    private List<TripRequest> getCompleteRequestsForCrewOwner(User crewOwner) {
        List<Crew> crews = crewRepository.findByCrewOwner(crewOwner);
        return requestRepository.findByCrewInAndStatusIn(crews, getCompleteStatuses());
    }

    private List<TripRequest> getCompleteRequestsForShipOwner(User shipOwner) {
        List<Ship> ships = shipRepository.findByOwner(shipOwner);
        return requestRepository.findByShipInAndStatusIn(ships, getCompleteStatuses());
    }

    private List<TripRequest> getCancelledRequestsForCrewOwner(User crewOwner) {
        List<Crew> crews = crewRepository.findByCrewOwner(crewOwner);
        return requestRepository.findByCrewInAndStatusIn(crews, getCancelledStatuses());
    }

    private List<TripRequest> getCancelledRequestsForShipOwner(User crewOwner) {
        List<Ship> ships = shipRepository.findByOwner(crewOwner);
        return requestRepository.findByShipInAndStatusIn(ships, getCancelledStatuses());
    }

    private List<TripRequest> getRequestsForUserWithStatuses(User user, List<TripRequestStatus> statuses) {
        if (user.getUserType().getName().equals(TRAVELLER_ROLE)) {
            return requestRepository.findByTravelerAndStatusIn(user, statuses);
        } else {
            throw new IllegalArgumentException(String.format("User %s is not a traveller", user.getNick()));
        }
    }

    private List<TripRequestStatus> getCancelledStatuses() {
        return Stream.of(CANCELLED, REJECTED).collect(Collectors.toList());
    }

    private List<TripRequestStatus> getPendingStatuses() {
        return Stream.of(PENDING, APPROVED_BY_SHIP, APPROVED_BY_CREW, APPROVED_BY_BOTH)
                .collect(Collectors.toList());
    }

    private List<TripRequestStatus> getCompleteStatuses() {
        return Stream.of(COMPLETE).collect(Collectors.toList());
    }

    private List<TripRequestStatus> getApprovedShipStatuses() {
        return Stream.of(COMPLETE, APPROVED_BY_SHIP, APPROVED_BY_BOTH)
                .collect(Collectors.toList());
    }

    private List<TripRequestStatus> getApprovedCrewStatuses() {
        return Stream.of(COMPLETE, APPROVED_BY_CREW, APPROVED_BY_BOTH)
                .collect(Collectors.toList());
    }

}
