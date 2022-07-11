package itmo.mpi.controller;

import itmo.mpi.dto.TripOption;
import itmo.mpi.dto.TripRequestDto;
import itmo.mpi.entity.Crew;
import itmo.mpi.entity.Ship;
import itmo.mpi.entity.TripRequest;
import itmo.mpi.model.TripRatingRequest;
import itmo.mpi.service.OptionsLookUpService;
import itmo.mpi.service.TripRequestInfoService;
import itmo.mpi.service.TripRequestManipulationService;
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
    private final TripRequestManipulationService tripRequestManipulationService;
    private final TripRequestInfoService tripRequestInfoService;

    @PostMapping("/options")
    public List<TripOption> getExpeditionOptions(@RequestBody TripRequestDto request) {
        return optionsLookUpService.lookUpOptions(request);
    }

    @PostMapping("/create")
    public void createTripRequest(@RequestBody TripRequest request) {
        tripRequestManipulationService.createTripRequest(request, getCurrentUsername());
    }

    @PostMapping("/cancel")
    public void cancelTripRequest(@RequestBody TripRequest request) {
        tripRequestManipulationService.cancelRequest(request, getCurrentUsername());
    }

    @PostMapping("/reject")
    public void rejectTripRequest(@RequestBody TripRequest request) {
        tripRequestManipulationService.rejectRequest(request, getCurrentUsername());
    }

    @PostMapping("/approve")
    public void approveTripRequest(@RequestBody TripRequest request) {
        tripRequestManipulationService.approveRequest(request, getCurrentUsername());
    }

    @GetMapping("/complete")
    public List<TripRequest> getCompleteTrips() {
        return tripRequestInfoService.getCompleteRequestsForUser(getCurrentUsername());
    }

    @GetMapping("/pending")
    public List<TripRequest> getPendingTrips() {
        return tripRequestInfoService.getPendingRequestsForUser(getCurrentUsername());
    }

    @GetMapping("/cancelled")
    public List<TripRequest> getCancelledTrips() {
        return tripRequestInfoService.getCancelledRequestsForUser(getCurrentUsername());
    }

    @PostMapping("/ship/pending")
    public List<TripRequest> getPendingTripsForShip(@RequestBody Ship ship) {
        return tripRequestInfoService.getPendingRequestsForShip(ship);
    }

    @PostMapping("/ship/complete")
    public List<TripRequest> getCompleteTripsForShip(@RequestBody Ship ship) {
        return tripRequestInfoService.getCompleteRequestsForShip(ship);
    }

    @PostMapping("/crew/pending")
    public List<TripRequest> getPendingTripsForCrew(@RequestBody Crew crew) {
        return tripRequestInfoService.getPendingRequestsForCrew(crew);
    }

    @PostMapping("/crew/complete")
    public List<TripRequest> getCompleteTripsForCrew(@RequestBody Crew crew) {
        return tripRequestInfoService.getCompleteRequestsForCrew(crew);
    }

    @DeleteMapping
    public void deleteRequest(@RequestBody TripRequest request) {
        tripRequestManipulationService.deleteRequest(request, getCurrentUsername());
    }

    @PostMapping("/crew/rate")
    public void rateTrip(@RequestBody TripRatingRequest request) {
        tripRequestManipulationService.rateTrip(request);
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildJson(exception.getMessage()));
    }

    private String buildJson(String error) {
        return String.format("{\"error\": \"%s\"}", error);
    }

}
