package itmo.mpi.controller;

import itmo.mpi.dto.TripOption;
import itmo.mpi.dto.TripRequestDto;
import itmo.mpi.entity.TripRequest;
import itmo.mpi.service.OptionsLookUpService;
import itmo.mpi.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/request")
public class TripRequestsController {

    private final OptionsLookUpService optionsLookUpService;
    private final TripService tripService;

    @PostMapping("/options")
    public List<TripOption> getExpeditionOptions(@RequestBody TripRequestDto request) {
        return optionsLookUpService.lookUpOptions(request);
    }

    @PostMapping("/create")
    public void createTripRequest(@RequestBody TripRequest request) {
        tripService.createTripRequest(request, getCurrentUsername());
    }

    @PostMapping("/cancel")
    public void cancelTripRequest(@RequestBody TripRequest request) {
        tripService.cancelRequest(request, getCurrentUsername());
    }

    @PostMapping("/reject")
    public void rejectTripRequest(@RequestBody TripRequest request) {
        tripService.rejectRequest(request, getCurrentUsername());
    }

    @DeleteMapping
    public void deleteRequest(@RequestBody TripRequest request) {
        tripService.deleteRequest(request, getCurrentUsername());
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }

}
