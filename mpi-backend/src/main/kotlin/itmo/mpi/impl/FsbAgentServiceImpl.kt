package itmo.mpi.impl

import itmo.mpi.entity.FsbAgent
import itmo.mpi.exception.IllegalRequestParamsException
import itmo.mpi.exception.IslandNotFoundException
import itmo.mpi.exception.UserNotFoundException
import itmo.mpi.model.CrewMemberResponse
import itmo.mpi.model.NewFsbAgentRequest
import itmo.mpi.repository.CrewRepository
import itmo.mpi.repository.FsbAgentRepository
import itmo.mpi.repository.IslandRepository
import itmo.mpi.repository.UserRepository
import itmo.mpi.service.FsbAgentService
import org.springframework.stereotype.Service

@Service
class FsbAgentServiceImpl(
    private val fsbAgentRepository: FsbAgentRepository,
    private val crewRepository: CrewRepository,
    private val userRepository: UserRepository,
    private val islandRepository: IslandRepository
) : FsbAgentService {

    override fun registerNewAgent(newFsbAgentRequest: NewFsbAgentRequest) = with(newFsbAgentRequest) {
        val newAgent = FsbAgent().apply {
            name = name
            surname = surname
            nick = nick
            password = password
        }
        fsbAgentRepository.save(newAgent)
        Unit
    }

    override fun getCrews(): List<CrewMemberResponse> = crewRepository
        .findAll()
        .mapNotNull {
            val user = it.crewOwner
            CrewMemberResponse(
                id = user.id,
                fullName = "${user.name} ${user.surname}",
                teamName = it.teamName,
                isPirate = user.isPirate
            )
        }

    override fun markAsPirate(userUid: Long) {
        val user = userRepository.findById(userUid).orElseThrow { UserNotFoundException(userUid) }
        if (user.userType.name == CREW_MANAGER) {
            userRepository.save(user.apply { isPirate = true })
        } else throw IllegalRequestParamsException("Incorrect user $userUid")
    }

    override fun markAsGoodPerson(userUid: Long) {
        val user = userRepository.findById(userUid).orElseThrow { UserNotFoundException(userUid) }
        if (user.userType.name == CREW_MANAGER) {
            userRepository.save(user.apply { isPirate = false })
        } else throw IllegalRequestParamsException("Incorrect user $userUid")
    }

    override fun markIslandAsDangerous(islandUid: Int) {
        val island = islandRepository.findById(islandUid).orElseThrow { IslandNotFoundException(islandUid) }
        islandRepository.save(island.apply { hasPirates = true })
    }

    override fun markIslandAsSafe(islandUid: Int) {
        val island = islandRepository.findById(islandUid).orElseThrow { IslandNotFoundException(islandUid) }
        islandRepository.save(island.apply { hasPirates = false })
    }

    companion object {
        private const val CREW_MANAGER = "CREW_MANAGER"
    }
}