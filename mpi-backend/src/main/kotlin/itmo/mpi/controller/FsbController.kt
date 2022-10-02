package itmo.mpi.controller

import itmo.mpi.entity.Island
import itmo.mpi.model.CrewMemberResponse
import itmo.mpi.model.IslandResponse
import itmo.mpi.service.FsbAgentService
import itmo.mpi.service.IslandService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("mpi/fsb")
class FsbController(private val fsbAgentService: FsbAgentService, private val islandService: IslandService) {

    @GetMapping("/crews")
    fun getCrews(): List<CrewMemberResponse> = fsbAgentService.getCrews()

    @GetMapping("/island")
    fun getIsland(): List<IslandResponse> = islandService.allIslandsForFsb.toList()

    @PutMapping("crew/{crewUid}/pirate/{isPirate}")
    fun updateCrew(
        @PathVariable crewUid: Long,
        @PathVariable isPirate: Boolean
    ) = if (isPirate) fsbAgentService.markAsPirate(crewUid)
    else fsbAgentService.markAsGoodPerson(crewUid)

    @PutMapping("island/{islandUid}/pirate/{isPirate}")
    fun updateCrew(
        @PathVariable islandUid: Int,
        @PathVariable isPirate: Boolean
    ) = if (isPirate) fsbAgentService.markIslandAsDangerous(islandUid)
    else fsbAgentService.markIslandAsSafe(islandUid)
}