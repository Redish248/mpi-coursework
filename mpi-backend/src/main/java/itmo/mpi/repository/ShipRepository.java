package itmo.mpi.repository;

import itmo.mpi.entity.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Long> {

    @Query(value = "select sh.* from ship sh left join trip_request tr on sh.uid = tr.ship_id AND " +
            "tr.date_start <= :startDate\\:\\:date + (ceil(:distance\\:\\:float/sh.speed) * interval '1 day') AND tr.date_end >= " +
            ":startDate where tr.uid is null;", nativeQuery = true)
    List<Ship> getFreeShipsForTrip(@Param("startDate") LocalDate startDate, @Param("distance") int distance);

}
