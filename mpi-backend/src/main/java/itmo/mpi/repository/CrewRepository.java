package itmo.mpi.repository;

import itmo.mpi.entity.Crew;
import itmo.mpi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CrewRepository extends JpaRepository<Crew, Integer> {

    @Query(value = "select cr.* from crew cr left join trip_request tr on cr.uid = tr.crew_id and tr.date_start <= " +
            ":dateEnd and tr.date_end >= :dateStart AND tr.status IN ('COMPLETE', 'APPROVED_BY_CREW', 'APPROVED_BY_BOTH') where tr.uid is null;", nativeQuery = true)
    List<Crew> getFreeCrewsForDates(@Param("dateStart") LocalDate startDate, @Param("dateEnd") LocalDate endDate);

    List<Crew> findByCrewOwner(User crewOwner);

    Crew getCrewByCrewOwner(User crewOwner);
}
