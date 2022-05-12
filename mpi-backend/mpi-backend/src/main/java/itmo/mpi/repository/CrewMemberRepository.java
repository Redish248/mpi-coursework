package itmo.mpi.repository;

import itmo.mpi.entity.CrewMember;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrewMemberRepository extends CrudRepository<CrewMember, Long> {
}
