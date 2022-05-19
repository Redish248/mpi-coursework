package itmo.mpi.controller;

import itmo.mpi.entity.Island;
import itmo.mpi.repository.IslandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/island")
@RequiredArgsConstructor
public class IslandController {

    private final IslandRepository islandRepository;

    @GetMapping
    List<Island> getIslandsList() {
        return islandRepository.findAll();
    }

}
