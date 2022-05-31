package itmo.mpi.repository;

import itmo.mpi.entity.Crew;
import itmo.mpi.entity.Ship;
import itmo.mpi.entity.TripRequest;
import itmo.mpi.entity.TripRequestStatus;
import itmo.mpi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRequestRepository extends JpaRepository<TripRequest, Integer> {

    List<TripRequest> findByTravelerAndStatusIn(User traveler, List<TripRequestStatus> statuses);

    List<TripRequest> findByShipAndStatusIn(Ship ship, List<TripRequestStatus> statuses);

    List<TripRequest> findByCrewAndStatusIn(Crew crew, List<TripRequestStatus> statuses);

    List<TripRequest> findByShipInAndStatusIn(List<Ship> ships, List<TripRequestStatus> statuses);

    List<TripRequest> findByCrewInAndStatusIn(List<Crew> crews, List<TripRequestStatus> statuses);
}
