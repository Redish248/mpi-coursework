package itmo.mpi.repository;

import itmo.mpi.entity.Island;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IslandRepository extends CrudRepository<Island, Long> {
}
