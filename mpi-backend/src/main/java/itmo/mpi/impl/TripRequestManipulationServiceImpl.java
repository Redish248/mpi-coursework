package itmo.mpi.impl;

import itmo.mpi.entity.Crew;
import itmo.mpi.entity.Ship;
import itmo.mpi.entity.TripRequest;
import itmo.mpi.entity.TripRequestStatus;
import itmo.mpi.model.TripRatingRequest;
import itmo.mpi.repository.CrewRepository;
import itmo.mpi.repository.ShipRepository;
import itmo.mpi.repository.TripRequestRepository;
import itmo.mpi.repository.UserRepository;
import itmo.mpi.service.TripRequestInfoService;
import itmo.mpi.service.TripRequestManipulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static itmo.mpi.entity.TripRequestStatus.CANCELLED;
import static itmo.mpi.entity.TripRequestStatus.COMPLETE;
import static itmo.mpi.entity.TripRequestStatus.ENDED;
import static itmo.mpi.entity.TripRequestStatus.PENDING;
import static itmo.mpi.entity.TripRequestStatus.REJECTED;

@RequiredArgsConstructor
@Service
public class TripRequestManipulationServiceImpl implements TripRequestManipulationService {

    private final TripRequestRepository tripRequestRepository;
    private final CrewRepository crewRepository;
    private final ShipRepository shipRepository;
    private final UserRepository userRepository;
    private final TripRequestInfoService requestInfoService;

    @Override
    public void createTripRequest(TripRequest request, String username) {
        if (username != null) {
            request.setTraveler(userRepository.findByNick(username));
            request.setStatus(PENDING);

            tripRequestRepository.save(request);
        } else {
            throw new IllegalArgumentException("Can't create request for unknown user");
        }
    }

    @Override
    public void cancelRequest(TripRequest request, String username) {
        TripRequest tripRequest = tripRequestRepository.getById(request.getId());
        if (tripRequest.getTraveler().getNick().equals(username)) {
            if (request.getStatus() == COMPLETE || request.getStatus() == REJECTED) {
                throw new IllegalArgumentException("Completed and rejected requests can't be cancelled");
            } else {
                tripRequest.setStatus(TripRequestStatus.CANCELLED);
                tripRequestRepository.save(tripRequest);
            }
        } else {
            throw new IllegalArgumentException("Request can only be cancelled by traveller");
        }
    }

    @Override
    public void rejectRequest(TripRequest request, String username) {
        TripRequest tripRequest = tripRequestRepository.getById(request.getId());
        if (tripRequest.getCrew().getCrewOwner().getNick().equals(username) ||
                tripRequest.getShip().getOwner().getNick().equals(username)) {
            if (tripRequest.getStatus() == COMPLETE || tripRequest.getStatus() == CANCELLED) {
                throw new IllegalArgumentException("Complete and cancelled requests can't be rejected");
            } else {
                tripRequest.setStatus(TripRequestStatus.REJECTED);
                tripRequestRepository.save(tripRequest);
            }
        } else {
            throw new IllegalArgumentException("Request can only be rejected by ship or crew owner");
        }
    }

    @Override
    public void deleteRequest(TripRequest request, String username) {
        TripRequest tripRequest = tripRequestRepository.getById(request.getId());
        if (tripRequest.getTraveler().getNick().equals(username)) {
            tripRequestRepository.delete(tripRequest);
        } else {
            throw new IllegalArgumentException("Request can only be deleted by traveller");
        }
    }

    @Override
    public void approveRequest(TripRequest request, String username) {
        TripRequest tripRequest = tripRequestRepository.getById(request.getId());
        if (tripRequest.getTraveler().getNick().equals(username)) {
            approveRequestByTraveller(tripRequest);
        } else if (tripRequest.getCrew().getCrewOwner().getNick().equals(username)) {
            approveRequestByCrew(tripRequest);
        } else if (tripRequest.getShip().getOwner().getNick().equals(username)) {
            approveRequestByShip(tripRequest);
        } else {
            throw new IllegalArgumentException("Trip request status can only be updated by one of the parties");
        }
    }

    @Override
    public void rateTrip(TripRatingRequest request) {
        TripRequest trip = tripRequestRepository.getById(request.getTripId());
        Crew crew = trip.getCrew();
        Ship ship = trip.getShip();

        crew.setRatesNumber(crew.getRatesNumber() + 1);
        crew.setRatesAverage((crew.getRatesAverage() + request.getCrew())/crew.getRatesNumber());

        ship.setRatesNumber(ship.getRatesNumber() + 1);
        ship.setRatesAverage((ship.getRatesAverage() + request.getShip())/ship.getRatesNumber());

        trip.setIsRated(true);

        tripRequestRepository.save(trip);
        shipRepository.save(ship);
        crewRepository.save(crew);
    }

    @Override
    public void endTrip(TripRequest request) {
        request.setStatus(ENDED);
        tripRequestRepository.save(request);
    }

    private void approveRequestByTraveller(TripRequest tripRequest) {
        switch (tripRequest.getStatus()) {
            case REJECTED, CANCELLED ->
                    throw new IllegalArgumentException("Cancelled and rejected requests can't be approved");
            case COMPLETE -> throw new IllegalArgumentException("This request is already approved");
            case PENDING, APPROVED_BY_CREW, APPROVED_BY_SHIP ->
                    throw new IllegalArgumentException("Request must be approved both by ship and crew before being" +
                            " approved by traveller");
            case APPROVED_BY_BOTH -> {
                tripRequest.setStatus(TripRequestStatus.COMPLETE);
                tripRequestRepository.save(tripRequest);
            }
        }
    }

    private void approveRequestByCrew(TripRequest request) {
        switch (request.getStatus()) {
            case APPROVED_BY_CREW:
            case APPROVED_BY_BOTH:
            case COMPLETE:
                throw new IllegalArgumentException("This request is already approved");
            case REJECTED:
            case CANCELLED:
                throw new IllegalArgumentException("Cancelled and rejected requests can't be approved");
            case APPROVED_BY_SHIP:
                if (ifCrewAvailable(request)) {
                    request.setStatus(TripRequestStatus.APPROVED_BY_BOTH);
                    break;
                } else {
                    throw new IllegalArgumentException("Crew is occupied by another trip on selected dates");
                }
            case PENDING:
                if (ifCrewAvailable(request)) {
                    request.setStatus(TripRequestStatus.APPROVED_BY_CREW);
                    break;
                } else {
                    throw new IllegalArgumentException("Crew is occupied by another trip on selected dates");
                }
        }
        tripRequestRepository.save(request);
    }

    private void approveRequestByShip(TripRequest request) {
        switch (request.getStatus()) {
            case APPROVED_BY_SHIP:
            case APPROVED_BY_BOTH:
            case COMPLETE:
                throw new IllegalArgumentException("This request is already approved");
            case REJECTED:
            case CANCELLED:
                throw new IllegalArgumentException("Cancelled and rejected requests can't be approved");
            case APPROVED_BY_CREW:
                if (isShipAvailable(request)) {
                    request.setStatus(TripRequestStatus.APPROVED_BY_BOTH);
                    break;
                } else {
                    throw new IllegalArgumentException("Ship is occupied by another trip in selected dates");
                }
            case PENDING:
                if (isShipAvailable(request)) {
                    request.setStatus(TripRequestStatus.APPROVED_BY_SHIP);
                    break;
                } else {
                    throw new IllegalArgumentException("Ship is occupied by another trip in selected dates");
                }
        }
        tripRequestRepository.save(request);
    }

    private boolean isShipAvailable(TripRequest request) {
        return requestInfoService.getApprovedRequestsForShip(request.getShip()).stream()
                .noneMatch(trip -> tripsOverlap(trip, request));
    }

    private boolean ifCrewAvailable(TripRequest request) {
        return requestInfoService.getApprovedRequestsForCrew(request.getCrew()).stream()
                .noneMatch(trip -> tripsOverlap(trip, request));
    }

    private boolean tripsOverlap(TripRequest existing, TripRequest newRequest) {
        return (existing.getDateStart().isBefore(newRequest.getDateEnd()) ||
                    existing.getDateStart().isEqual(newRequest.getDateEnd())) &&
                (existing.getDateEnd().isAfter(newRequest.getDateStart()) ||
                        existing.getDateEnd().isEqual(newRequest.getDateStart()));
    }

}
