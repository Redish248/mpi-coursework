package itmo.mpi.repository;

import itmo.mpi.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRequestRepository extends JpaRepository<TripRequest, Integer> {

    List<TripRequest> findByTravellerAndStatusIn(User traveller, List<TripRequestStatus> statuses);

    List<TripRequest> findByShipAndStatusIn(Ship ship, List<TripRequestStatus> statuses);

    List<TripRequest> findByCrewAndStatusIn(Crew crew, List<TripRequestStatus> statuses);

}
