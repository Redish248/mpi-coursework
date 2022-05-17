package itmo.mpi.service;

import itmo.mpi.entity.TripRequest;

public interface TripService {

    void createTripRequest(TripRequest request, String username);

    void cancelRequest(TripRequest request, String username);

    void rejectRequest(TripRequest request, String username);

    void deleteRequest(TripRequest request, String username);
}
