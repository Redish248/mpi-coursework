package itmo.mpi.impl

import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import itmo.mpi.entity.*
import itmo.mpi.exception.IllegalRequestParamsException
import itmo.mpi.exception.IslandNotFoundException
import itmo.mpi.exception.UserNotFoundException
import itmo.mpi.model.CrewMemberResponse
import itmo.mpi.model.NewFsbAgentRequest
import itmo.mpi.repository.CrewRepository
import itmo.mpi.repository.FsbAgentRepository
import itmo.mpi.repository.IslandRepository
import itmo.mpi.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.util.*
import kotlin.test.assertTrue

class FsbServiceImplTest {

    private val fbsAgentRepository: FsbAgentRepository = mockk()
    private val crewRepository: CrewRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private val islandRepository: IslandRepository = mockk()

    @InjectMockKs
    private lateinit var fsbServiceImpl: FsbAgentServiceImpl

    @BeforeEach
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Test registering new agent`() {
        every { fbsAgentRepository.save(any()) } returns FsbAgent()

        val newFsbAgentRequest = NewFsbAgentRequest("name", "surname", "nick", "pass")
        fsbServiceImpl.registerNewAgent(newFsbAgentRequest)

        verify { fbsAgentRepository.save(any()) }
    }

    @Test
    fun `Test getting crews`() {
        every { crewRepository.findAll() } returns mutableListOf(Crew().apply {
            teamName = "test"
            crewOwner = User().apply {
                id = 1
                name = "name"
                surname = "surname"
                teamName = "teamName"
                isPirate = true
            }
        })

        val result = fsbServiceImpl.getCrews()

        assertAll(
                { assertEquals(1, result[0].id) },
                { assertEquals("name surname", result[0].fullName) },
                { assertEquals("teamName", result[0].teamName) },
                { assertTrue(result[0].isPirate) }
        )
    }

    @Test
    fun `check mark crew as pirate`() {
        val crew = User().apply {
            id = 1
            isPirate = false
            userType = UserRole().apply {
                id = 1
                name = CREW_MANAGER
            }
        }
        val savedUser = slot<User>()
        every { userRepository.findById(crew.id.toLong()) } returns Optional.of(crew)
        every { userRepository.save(capture(savedUser)) } returns crew

        fsbServiceImpl.markAsPirate(crew.id.toLong())

        assertThat(savedUser.captured.isPirate).isTrue
    }

    @Test
    fun `check mark crew as pirate for other role`() {
        val crew = User().apply {
            id = 1
            isPirate = false
            userType = UserRole().apply {
                id = 1
                name = SHIP_OWNER
            }
        }
        val savedUser = slot<User>()
        every { userRepository.findById(crew.id.toLong()) } returns Optional.of(crew)
        every { userRepository.save(capture(savedUser)) } returns crew

        val thrown = assertThrows(IllegalRequestParamsException::class.java) {
            fsbServiceImpl.markAsPirate(crew.id.toLong())
        }
        assertThat(thrown).hasMessageContaining(crew.id.toString())
    }

    @Test
    fun `check mark unknown crew as pirate`() {
        val unkownId = 1L
        every { userRepository.findById(unkownId) } returns Optional.empty()

        val thrown = assertThrows(UserNotFoundException::class.java) {
            fsbServiceImpl.markAsPirate(unkownId)
        }
        assertThat(thrown).hasMessageContaining(unkownId.toString())
    }

    @Test
    fun `check mark crew as good person`() {
        val crew = User().apply {
            id = 1
            isPirate = true
            userType = UserRole().apply {
                id = 1
                name = CREW_MANAGER
            }
        }
        val savedUser = slot<User>()
        every { userRepository.findById(crew.id.toLong()) } returns Optional.of(crew)
        every { userRepository.save(capture(savedUser)) } returns crew

        fsbServiceImpl.markAsGoodPerson(crew.id.toLong())

        assertThat(savedUser.captured.isPirate).isFalse
    }

    @Test
    fun `check mark crew as good person for other role`() {
        val crew = User().apply {
            id = 1
            isPirate = true
            userType = UserRole().apply {
                id = 1
                name = SHIP_OWNER
            }
        }
        val savedUser = slot<User>()
        every { userRepository.findById(crew.id.toLong()) } returns Optional.of(crew)
        every { userRepository.save(capture(savedUser)) } returns crew

        val thrown = assertThrows(IllegalRequestParamsException::class.java) {
            fsbServiceImpl.markAsGoodPerson(crew.id.toLong())
        }
        assertThat(thrown).hasMessageContaining(crew.id.toString())
    }

    @Test
    fun `check mark unknown crew as good person`() {
        val unkownId = 1L
        every { userRepository.findById(unkownId) } returns Optional.empty()

        val thrown = assertThrows(UserNotFoundException::class.java) {
            fsbServiceImpl.markAsGoodPerson(unkownId)
        }
        assertThat(thrown).hasMessageContaining(unkownId.toString())
    }

    @Test
    fun `check set island dangerous`() {
        val island = Island().apply { id = 4 }
        val savedIsland = slot<Island>()
        every { islandRepository.findById(island.id) } returns Optional.of(island)
        every { islandRepository.save(capture(savedIsland)) } returns island

        fsbServiceImpl.markIslandAsDangerous(island.id)
        assertThat(savedIsland.captured.hasPirates).isTrue
    }

    @Test
    fun `check set island safe`() {
        val island = Island().apply { id = 4 }
        val savedIsland = slot<Island>()
        every { islandRepository.findById(island.id) } returns Optional.of(island)
        every { islandRepository.save(capture(savedIsland)) } returns island

        fsbServiceImpl.markIslandAsSafe(island.id)
        assertThat(savedIsland.captured.hasPirates).isFalse
    }

    @Test
    fun `check set unknown island dangerous`() {
        val unknownId = 5
        every { islandRepository.findById(unknownId) } returns Optional.empty()

        val thrown = assertThrows(IslandNotFoundException::class.java) {
            fsbServiceImpl.markIslandAsDangerous(unknownId)
        }
        assertThat(thrown).hasMessageContaining(unknownId.toString())
    }

    @Test
    fun `check set unknown island safe`() {
        val unknownId = 5
        every { islandRepository.findById(unknownId) } returns Optional.empty()

        val thrown = assertThrows(IslandNotFoundException::class.java) {
            fsbServiceImpl.markIslandAsSafe(unknownId)
        }
        assertThat(thrown).hasMessageContaining(unknownId.toString())
    }

    companion object {
        private const val CREW_MANAGER = "CREW_MANAGER"
        private const val SHIP_OWNER = "SHIP_OWNER"
    }

}