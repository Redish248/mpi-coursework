package itmo.mpi.repository;

import itmo.mpi.entity.Island;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IslandRepository extends JpaRepository<Island, Integer> {
}
