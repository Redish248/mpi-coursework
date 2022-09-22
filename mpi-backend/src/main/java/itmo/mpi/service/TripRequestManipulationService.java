package itmo.mpi.service;

import itmo.mpi.entity.TripRequest;
import itmo.mpi.model.TripRatingRequest;

public interface TripRequestManipulationService {

    void createTripRequest(TripRequest request, String username);

    void cancelRequest(TripRequest request, String username);

    void rejectRequest(TripRequest request, String username);

    void deleteRequest(TripRequest request, String username);

    void approveRequest(TripRequest request, String username);

    void rateTrip(TripRatingRequest request);

    void endTrip(TripRequest request);

}
