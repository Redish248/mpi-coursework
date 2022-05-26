package itmo.mpi.controller;

import itmo.mpi.entity.Island;
import itmo.mpi.repository.IslandRepository;
import itmo.mpi.service.IslandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/island")
@RequiredArgsConstructor
public class IslandController {

    private final IslandService islandService;

    @GetMapping
    List<Island> getIslandsList() {
        return islandService.getIslands();
    }

}
