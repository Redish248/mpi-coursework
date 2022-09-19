package itmo.mpi.job;

import itmo.mpi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final UserService userService;

    @Scheduled(cron = "0 56 22 * * *")
    public void cleanRegistrationRequests() {
        userService.removeOldRegistrationRequests(LocalDate.now());
    }
}
