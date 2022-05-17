package itmo.mpi.repository;

import itmo.mpi.entity.TripRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRequestRepository extends JpaRepository<TripRequest, Long> {
}
