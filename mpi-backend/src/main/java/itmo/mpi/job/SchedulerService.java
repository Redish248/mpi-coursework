package itmo.mpi.job;

import itmo.mpi.entity.TripRequest;
import itmo.mpi.entity.TripRequestStatus;
import itmo.mpi.service.TripRequestInfoService;
import itmo.mpi.service.TripRequestManipulationService;
import itmo.mpi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final UserService userService;

    private final TripRequestManipulationService tripRequestManipulationService;

    private final TripRequestInfoService tripRequestInfoService;

    @Scheduled(cron = "0 30 00 * * *")
    public void cleanRegistrationRequests() {
        userService.removeOldRegistrationRequests(LocalDate.now());
    }

    @Scheduled(cron = "0 00 01 * * *")
    public void endRequestsByDate() {
        List<TripRequest> needToEnd = tripRequestInfoService.getTripsByStatus(TripRequestStatus.COMPLETE);
        needToEnd.stream().filter(tripRequest ->
            LocalDate.now().isAfter(tripRequest.getDateEnd())
        ).forEach(tripRequestManipulationService::endTrip);
    }
}
