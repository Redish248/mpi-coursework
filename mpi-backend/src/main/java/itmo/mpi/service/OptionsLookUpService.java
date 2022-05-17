package itmo.mpi.service;

import itmo.mpi.dto.TripOption;
import itmo.mpi.dto.TripRequestDto;

import java.util.List;

/**
 * An interface responsible for looking up expedition options (crew + ship)
 * for the given expedition request (route + budget)
 */
public interface OptionsLookUpService {

    List<TripOption> lookUpOptions(TripRequestDto tripRequestDto);

}
