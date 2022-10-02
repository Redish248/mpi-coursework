package itmo.mpi.service;

import itmo.mpi.entity.Island;
import itmo.mpi.model.IslandResponse;

import java.util.List;

public interface IslandService {

    List<Island> getIslands();

    List<IslandResponse> getAllIslandsForFsb();
}
