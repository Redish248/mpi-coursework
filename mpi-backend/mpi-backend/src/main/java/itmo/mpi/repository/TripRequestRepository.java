package itmo.mpi.repository;

import itmo.mpi.entity.TripRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRequestRepository extends CrudRepository<TripRequest, Long> {
}
