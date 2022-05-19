package itmo.mpi.service.impl;

import itmo.mpi.entity.TripRequest;
import itmo.mpi.entity.TripRequestStatus;
import itmo.mpi.repository.TripRequestRepository;
import itmo.mpi.repository.UserRepository;
import itmo.mpi.service.TripRequestInfoService;
import itmo.mpi.service.TripRequestManipulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static itmo.mpi.entity.TripRequestStatus.*;

@RequiredArgsConstructor
@Service
public class TripRequestManipulationServiceImpl implements TripRequestManipulationService {

    private final TripRequestRepository repository;
    private final UserRepository userRepository;
    private final TripRequestInfoService requestInfoService;

    @Override
    public void createTripRequest(TripRequest request, String username) {
        if (username != null) {
            request.setTraveler(userRepository.findByNick(username));
            request.setStatus(PENDING);

            repository.save(request);
        } else {
            throw new IllegalArgumentException("Can't create request for unknown user");
        }
    }

    @Override
    public void cancelRequest(TripRequest request, String username) {
        TripRequest tripRequest = repository.getById(request.getId());
        if (tripRequest.getTraveler().getNick().equals(username)) {
            if (request.getStatus() == COMPLETE || request.getStatus() == REJECTED) {
                throw new IllegalArgumentException("Completed and rejected requests can't be cancelled");
            } else {
                tripRequest.setStatus(TripRequestStatus.CANCELLED);
                repository.save(tripRequest);
            }
        } else {
            throw new IllegalArgumentException("Request can only be cancelled by traveller");
        }
    }

    @Override
    public void rejectRequest(TripRequest request, String username) {
        TripRequest tripRequest = repository.getById(request.getId());
        if (tripRequest.getCrew().getCrewOwner().getNick().equals(username) ||
                tripRequest.getShip().getOwner().getNick().equals(username)) {
            if (tripRequest.getStatus() == COMPLETE || tripRequest.getStatus() == CANCELLED) {
                throw new IllegalArgumentException("Complete and cancelled requests can't be rejected");
            } else {
                tripRequest.setStatus(TripRequestStatus.REJECTED);
                repository.save(tripRequest);
            }
        } else {
            throw new IllegalArgumentException("Request can only be rejected by ship or crew owner");
        }
    }

    @Override
    public void deleteRequest(TripRequest request, String username) {
        TripRequest tripRequest = repository.getById(request.getId());
        if (tripRequest.getTraveler().getNick().equals(username)) {
            repository.delete(tripRequest);
        } else {
            throw new IllegalArgumentException("Request can only be deleted by traveller");
        }
    }

    @Override
    public void approveRequest(TripRequest request, String username) {
        TripRequest tripRequest = repository.getById(request.getId());
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

    private void approveRequestByTraveller(TripRequest tripRequest) {
        switch (tripRequest.getStatus()) {
            case REJECTED:
            case CANCELLED:
                throw new IllegalArgumentException("Cancelled and rejected requests can't be approved");
            case COMPLETE:
                throw new IllegalArgumentException("This request is already approved");
            case PENDING:
            case APPROVED_BY_CREW:
            case APPROVED_BY_SHIP:
                throw new IllegalArgumentException("Request must be approved both by ship and crew before being" +
                        " approved by traveller");
            case APPROVED_BY_BOTH:
                tripRequest.setStatus(TripRequestStatus.COMPLETE);
                repository.save(tripRequest);
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
                request.setStatus(TripRequestStatus.APPROVED_BY_BOTH);
                break;
            case PENDING:
                if (ifCrewAvailable(request)) {
                    request.setStatus(TripRequestStatus.APPROVED_BY_CREW);
                    break;
                } else {
                    throw new IllegalArgumentException("Crew is occupied by another trip on selected dates");
                }
        }
        repository.save(request);
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
                request.setStatus(TripRequestStatus.APPROVED_BY_BOTH);
                break;
            case PENDING:
                if (isShipAvailable(request)) {
                    request.setStatus(TripRequestStatus.APPROVED_BY_SHIP);
                    break;
                } else {
                    throw new IllegalArgumentException("Ship is occupied by another trip in selected dates");
                }
        }
        repository.save(request);
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
