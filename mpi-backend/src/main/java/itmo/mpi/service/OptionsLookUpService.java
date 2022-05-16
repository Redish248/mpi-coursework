package itmo.mpi.service;

import itmo.mpi.dto.ExpeditionOption;
import itmo.mpi.dto.ExpeditionRequest;

import java.util.List;

/**
 * An interface responsible for looking up expedition options (crew + ship)
 * for the given expedition request (route + budget)
 */
public interface OptionsLookUpService {

    List<ExpeditionOption> lookUpOptions(ExpeditionRequest expeditionRequest);

}
