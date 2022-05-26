package itmo.mpi.service;

import itmo.mpi.entity.Crew;
import itmo.mpi.entity.Ship;
import itmo.mpi.entity.TripRequest;

import java.util.List;

public interface TripRequestInfoService {

    List<TripRequest> getPendingRequestsForUser(String username);

    List<TripRequest> getCompleteRequestsForUser(String username);

    List<TripRequest> getCancelledRequestsForUser(String username);

    List<TripRequest> getPendingRequestsForShip(Ship ship);

    List<TripRequest> getPendingRequestsForCrew(Crew crew);

    List<TripRequest> getCompleteRequestsForShip(Ship ship);

    List<TripRequest> getCompleteRequestsForCrew(Crew crew);

    List<TripRequest> getApprovedRequestsForShip(Ship ship);

    List<TripRequest> getApprovedRequestsForCrew(Crew crew);

}
