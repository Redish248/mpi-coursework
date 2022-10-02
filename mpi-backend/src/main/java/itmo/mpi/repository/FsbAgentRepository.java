package itmo.mpi.repository;

import itmo.mpi.entity.FsbAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FsbAgentRepository extends JpaRepository<FsbAgent, Integer> {

}
