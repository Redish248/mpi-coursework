package itmo.mpi.repository;

import itmo.mpi.entity.Crew;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrewRepository extends CrudRepository<Crew, Long> {
}
