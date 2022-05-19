package itmo.mpi.service.impl;

import itmo.mpi.entity.TripRequest;
import itmo.mpi.entity.TripRequestStatus;
import itmo.mpi.repository.TripRequestRepository;
import itmo.mpi.repository.UserRepository;
import itmo.mpi.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TripServiceImpl implements TripService {

    private final TripRequestRepository repository;
    private final UserRepository userRepository;

    @Override
    public void createTripRequest(TripRequest request, String username) {
        if (username != null) {
            request.setTraveler(userRepository.findByNick(username));
            request.setStatus(TripRequestStatus.PENDING);

            repository.save(request);
        } else {
            throw new IllegalArgumentException("Can't create request for unknown user");
        }
    }

    @Override
    public void cancelRequest(TripRequest request, String username) {
        TripRequest tripRequest = repository.getById(request.getId());
        if (tripRequest.getTraveler().getNick().equals(username)) {
            tripRequest.setStatus(TripRequestStatus.CANCELLED);
            repository.save(tripRequest);
        } else {
            throw new IllegalArgumentException("Request can only be cancelled by traveller");
        }
    }

    @Override
    public void rejectRequest(TripRequest request, String username) {
        TripRequest tripRequest = repository.getById(request.getId());
        if (tripRequest.getCrew().getCrewOwner().getNick().equals(username) ||
                tripRequest.getShip().getOwner().getNick().equals(username)) {
            tripRequest.setStatus(TripRequestStatus.REJECTED);
            repository.save(tripRequest);
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
}
