package itmo.mpi.impl

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verify
import itmo.mpi.entity.*
import itmo.mpi.model.TripRatingRequest
import itmo.mpi.repository.CrewRepository
import itmo.mpi.repository.ShipRepository
import itmo.mpi.repository.TripRequestRepository
import itmo.mpi.repository.UserRepository
import org.assertj.core.util.Lists
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExtendWith(MockKExtension::class)
class TripRequestManipulationServiceImplTest {

    @MockK
    private lateinit var tripRequestRepository: TripRequestRepository

    @MockK
    private lateinit var crewRepository: CrewRepository

    @MockK
    private lateinit var shipRepository: ShipRepository

    @MockK
    private lateinit var userRepository: UserRepository

    @MockK
    private lateinit var tripRequestInfoServiceImpl: TripRequestInfoServiceImpl

    @InjectMockKs
    private lateinit var tripRequestManipulationServiceImpl: TripRequestManipulationServiceImpl

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Test creating trip request`() {
        justRun { tripRequestRepository.save(any()) }
        every { userRepository.findByNick(any()) } returns mockedUser

        tripRequestManipulationServiceImpl.createTripRequest(mockedTripRequest, "nick")

        verify { tripRequestRepository.save(mockedTripRequest) }

    }

    @Test
    fun `Test creating trip request with empty username`() {
        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.createTripRequest(TripRequest(), null) }
    }

    @Test
    fun `Test cancelling trip request`() {
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest
        justRun { tripRequestRepository.save(any()) }
        every { userRepository.findByNick(any()) } returns mockedUser

        tripRequestManipulationServiceImpl.cancelRequest(mockedTripRequest, "nick")

        verify { tripRequestRepository.save(mockedTripRequest) }

        assertEquals(TripRequestStatus.CANCELLED, mockedTripRequest.status)
    }

    @Test
    fun `Test cancelling trip request for COMPLETE status`() {
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest

        mockedTripRequest.status = TripRequestStatus.COMPLETE

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.cancelRequest(mockedTripRequest, "nick") }
    }

    @Test
    fun `Test cancelling trip request for REJECTED status`() {
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest

        mockedTripRequest.status = TripRequestStatus.REJECTED

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.cancelRequest(mockedTripRequest, "nick") }
    }

    @Test
    fun `Test cancelling trip request with incorrect username`() {
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.cancelRequest(mockedTripRequest, "test") }
    }

    @Test
    fun `Test rejecting trip request`() {
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest
        justRun { tripRequestRepository.save(any()) }
        every { userRepository.findByNick(any()) } returns mockedUser

        tripRequestManipulationServiceImpl.rejectRequest(mockedTripRequest, "nick")

        verify { tripRequestRepository.save(mockedTripRequest) }

        assertEquals(TripRequestStatus.REJECTED, mockedTripRequest.status)
    }

    @Test
    fun `Test rejecting trip request for COMPLETE status`() {
        mockedTripRequest.status = TripRequestStatus.COMPLETE

        every { tripRequestRepository.getById(any()) } returns mockedTripRequest

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.rejectRequest(mockedTripRequest, "nick") }
    }

    @Test
    fun `Test rejecting trip request for CANCELLED status`() {
        mockedTripRequest.status = TripRequestStatus.CANCELLED
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest
        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.rejectRequest(mockedTripRequest, "nick") }
    }

    @Test
    fun `Test rejecting trip request with incorrect username`() {
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.rejectRequest(mockedTripRequest, "test") }
    }

    @Test
    fun `Test deleting trip`() {
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest
        justRun { tripRequestRepository.delete(any()) }

        tripRequestManipulationServiceImpl.deleteRequest(mockedTripRequest, "nick")

        verify { tripRequestRepository.delete(mockedTripRequest) }
    }

    @Test
    fun `Test deleting trip with incorrect username`() {
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.deleteRequest(mockedTripRequest, "test") }
    }

    @Test
    fun `Test approving trip for traveler for REJECTED status`() {
        mockedTripRequest.status = TripRequestStatus.REJECTED
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "nick") }
    }

    @Test
    fun `Test approving trip for traveler for CANCELLED status`() {
        mockedTripRequest.status = TripRequestStatus.CANCELLED
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "nick") }
    }

    @Test
    fun `Test approving trip for traveler for COMPLETE status`() {
        mockedTripRequest.status = TripRequestStatus.COMPLETE
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "nick") }
    }

    @Test
    fun `Test approving trip for traveler for PENDING status`() {
        mockedTripRequest.status = TripRequestStatus.PENDING
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "nick") }
    }

    @Test
    fun `Test approving trip for traveler for APPROVED_BY_CREW status`() {
        mockedTripRequest.status = TripRequestStatus.APPROVED_BY_CREW
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "nick") }
    }

    @Test
    fun `Test approving trip for traveler for APPROVED_BY_SHIP status`() {
        mockedTripRequest.status = TripRequestStatus.APPROVED_BY_SHIP
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "nick") }
    }

    @Test
    fun `Test approving trip for traveler for APPROVED_BY_BOTH status`() {
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest
        every { tripRequestRepository.save(any()) } returns mockedTripRequest

        tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "nick")

        verify { tripRequestRepository.save(mockedTripRequest) }
        assertEquals(TripRequestStatus.COMPLETE, mockedTripRequest.status)

    }

    @Test
    fun `Test approving trip for crew for APPROVED_BY_CREW status`() {
        val mockedTraveler = User().apply {
            nick = "traveler"
        }
        mockedTripRequest.traveler = mockedTraveler
        mockedTripRequest.status = TripRequestStatus.APPROVED_BY_CREW
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "nick") }
    }

    @Test
    fun `Test approving trip for crew for APPROVED_BY_BOTH status`() {
        val mockedTraveler = User().apply {
            nick = "traveler"
        }
        mockedTripRequest.traveler = mockedTraveler
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "nick") }
    }

    @Test
    fun `Test approving trip for crew for COMPLETE status`() {
        val mockedTraveler = User().apply {
            nick = "traveler"
        }
        mockedTripRequest.traveler = mockedTraveler
        mockedTripRequest.status = TripRequestStatus.COMPLETE
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "nick") }
    }

    @Test
    fun `Test approving trip for crew for REJECTED status`() {
        val mockedTraveler = User().apply {
            nick = "traveler"
        }
        mockedTripRequest.traveler = mockedTraveler
        mockedTripRequest.status = TripRequestStatus.REJECTED
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "nick") }
    }

    @Test
    fun `Test approving trip for crew for CANCELLED status`() {
        val mockedTraveler = User().apply {
            nick = "traveler"
        }
        mockedTripRequest.traveler = mockedTraveler
        mockedTripRequest.status = TripRequestStatus.CANCELLED
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "nick") }
    }

    @Test
    fun `Test approving trip for crew for APPROVED_BY_SHIP status for not available crew`() {
        val mockedTraveler = User().apply {
            nick = "traveler"
        }
        mockedTripRequest.traveler = mockedTraveler
        mockedTripRequest.status = TripRequestStatus.APPROVED_BY_SHIP
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest
        every { tripRequestInfoServiceImpl.getApprovedRequestsForCrew(any()) } answers { Lists.newArrayList(mockedTripRequest) }

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "nick") }
    }

    @Test
    fun `Test approving trip for crew for APPROVED_BY_SHIP status for available crew`() {
        val mockedTraveler = User().apply {
            nick = "traveler"
        }
        mockedTripRequest.traveler = mockedTraveler
        mockedTripRequest.status = TripRequestStatus.APPROVED_BY_SHIP
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest
        every { tripRequestRepository.save(any()) } returns mockedTripRequest
        every { tripRequestInfoServiceImpl.getApprovedRequestsForCrew(any()) } answers { Lists.newArrayList() }

        tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "nick")

        verify { tripRequestRepository.save(mockedTripRequest) }
        assertEquals(TripRequestStatus.APPROVED_BY_BOTH, mockedTripRequest.status)
    }

    @Test
    fun `Test approving trip for crew for PENDING status for not available crew`() {
        val mockedTraveler = User().apply {
            nick = "traveler"
        }
        mockedTripRequest.traveler = mockedTraveler
        mockedTripRequest.status = TripRequestStatus.PENDING
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest
        every { tripRequestInfoServiceImpl.getApprovedRequestsForCrew(any()) } answers { Lists.newArrayList(mockedTripRequest) }

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "nick") }
    }

    @Test
    fun `Test approving trip for crew for PENDING status for available crew`() {
        val mockedTraveler = User().apply {
            nick = "traveler"
        }
        mockedTripRequest.traveler = mockedTraveler
        mockedTripRequest.status = TripRequestStatus.PENDING
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest
        every { tripRequestRepository.save(any()) } returns mockedTripRequest
        every { tripRequestInfoServiceImpl.getApprovedRequestsForCrew(any()) } answers { Lists.newArrayList() }

        tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "nick")

        verify { tripRequestRepository.save(mockedTripRequest) }
        assertEquals(TripRequestStatus.APPROVED_BY_CREW, mockedTripRequest.status)
    }

    @Test
    fun `Test approving trip with incorrect username`() {
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest
        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "test") }
    }

    @Test
    fun `Test approving trip for ship for APPROVED_BY_CREW status`() {
        val mockedTraveler = User().apply {
            nick = "traveler"
        }

        val mockedCrew = Crew().apply {
            crewOwner = mockedTraveler
        }
        mockedTripRequest.traveler = mockedTraveler
        mockedTripRequest.crew = mockedCrew
        mockedTripRequest.status = TripRequestStatus.APPROVED_BY_SHIP
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "nick") }
    }

    @Test
    fun `Test approving trip for ship for APPROVED_BY_BOTH status`() {
        val mockedTraveler = User().apply {
            nick = "traveler"
        }

        val mockedCrew = Crew().apply {
            crewOwner = mockedTraveler
        }
        mockedTripRequest.traveler = mockedTraveler
        mockedTripRequest.crew = mockedCrew
        mockedTripRequest.status = TripRequestStatus.APPROVED_BY_BOTH
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "nick") }
    }

    @Test
    fun `Test approving trip for ship for COMPLETE status`() {
        val mockedTraveler = User().apply {
            nick = "traveler"
        }

        val mockedCrew = Crew().apply {
            crewOwner = mockedTraveler
        }
        mockedTripRequest.traveler = mockedTraveler
        mockedTripRequest.crew = mockedCrew
        mockedTripRequest.status = TripRequestStatus.COMPLETE
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "nick") }
    }

    @Test
    fun `Test approving trip for ship for REJECTED status`() {
        val mockedTraveler = User().apply {
            nick = "traveler"
        }

        val mockedCrew = Crew().apply {
            crewOwner = mockedTraveler
        }
        mockedTripRequest.traveler = mockedTraveler
        mockedTripRequest.crew = mockedCrew
        mockedTripRequest.status = TripRequestStatus.REJECTED
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "nick") }
    }

    @Test
    fun `Test approving trip for ship for CANCELLED status`() {
        val mockedTraveler = User().apply {
            nick = "traveler"
        }

        val mockedCrew = Crew().apply {
            crewOwner = mockedTraveler
        }
        mockedTripRequest.traveler = mockedTraveler
        mockedTripRequest.crew = mockedCrew
        mockedTripRequest.status = TripRequestStatus.CANCELLED
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "nick") }
    }

    @Test
    fun `Test approving trip for ship for APPROVED_BY_CREW status for not available ship`() {
        val mockedTraveler = User().apply {
            nick = "traveler"
        }
        val mockedCrew = Crew().apply {
            crewOwner = mockedTraveler
        }
        mockedTripRequest.traveler = mockedTraveler
        mockedTripRequest.crew = mockedCrew
        mockedTripRequest.status = TripRequestStatus.APPROVED_BY_CREW
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest
        every { tripRequestInfoServiceImpl.getApprovedRequestsForShip(any()) } answers { Lists.newArrayList(mockedTripRequest) }

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "nick") }
    }

    @Test
    fun `Test approving trip for ship for APPROVED_BY_CREW status for available ship`() {
        val mockedTraveler = User().apply {
            nick = "traveler"
        }
        val mockedCrew = Crew().apply {
            crewOwner = mockedTraveler
        }
        mockedTripRequest.traveler = mockedTraveler
        mockedTripRequest.crew = mockedCrew
        mockedTripRequest.status = TripRequestStatus.APPROVED_BY_CREW
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest
        every { tripRequestRepository.save(any()) } returns mockedTripRequest
        every { tripRequestInfoServiceImpl.getApprovedRequestsForShip(any()) } answers { Lists.newArrayList() }

        tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "nick")

        verify { tripRequestRepository.save(mockedTripRequest) }
        assertEquals(TripRequestStatus.APPROVED_BY_BOTH, mockedTripRequest.status)
    }

    @Test
    fun `Test approving trip for ship for PENDING status for not available ship`() {
        val mockedTraveler = User().apply {
            nick = "traveler"
        }
        val mockedCrew = Crew().apply {
            crewOwner = mockedTraveler
        }
        mockedTripRequest.traveler = mockedTraveler
        mockedTripRequest.crew = mockedCrew
        mockedTripRequest.status = TripRequestStatus.PENDING
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest
        every { tripRequestInfoServiceImpl.getApprovedRequestsForShip(any()) } answers { Lists.newArrayList(mockedTripRequest) }

        assertThrows<IllegalArgumentException> { tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "nick") }
    }

    @Test
    fun `Test approving trip for ship for PENDING status for available ship`() {
        val mockedTraveler = User().apply {
            nick = "traveler"
        }
        val mockedCrew = Crew().apply {
            crewOwner = mockedTraveler
        }
        mockedTripRequest.traveler = mockedTraveler
        mockedTripRequest.crew = mockedCrew
        mockedTripRequest.status = TripRequestStatus.PENDING
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest
        every { tripRequestRepository.save(any()) } returns mockedTripRequest
        every { tripRequestInfoServiceImpl.getApprovedRequestsForShip(any()) } answers { Lists.newArrayList() }

        tripRequestManipulationServiceImpl.approveRequest(mockedTripRequest, "nick")

        verify { tripRequestRepository.save(mockedTripRequest) }
        assertEquals(TripRequestStatus.APPROVED_BY_SHIP, mockedTripRequest.status)
    }

    @Test
    fun `Test rating trip`() {
        every { tripRequestRepository.getById(any()) } returns mockedTripRequest
        every { tripRequestRepository.save(any()) } answers { nothing }
        every { shipRepository.save(any()) } answers { nothing }
        every { crewRepository.save(any()) } answers { nothing }

        tripRequestManipulationServiceImpl.rateTrip(mockedTripRatingRequest)

        verify { tripRequestRepository.save(mockedTripRequest) }
        verify { shipRepository.save(mockedShip) }
        verify { crewRepository.save(mockedCrew) }

        assertAll(
                { assertEquals(2, mockedCrew.ratesNumber) },
                { assertEquals(4.0, mockedCrew.ratesAverage) },
                { assertEquals(2, mockedShip.ratesNumber) },
                { assertEquals(4.0, mockedShip.ratesAverage) },
                { assertTrue { mockedTripRequest.isRated } }
        )
    }

    @Test
    fun `Test ending trip`() {
        every { tripRequestRepository.save(any()) } answers { nothing }
        tripRequestManipulationServiceImpl.endTrip(mockedTripRequest)

        verify { tripRequestRepository.save(mockedTripRequest) }

        assertEquals(TripRequestStatus.ENDED, mockedTripRequest.status)
    }

    private val mockedIsland = Island().apply {
        id = 1
        name = "name"
        xCoordinate = 1
        yCoordinate = 1
        hasPirates = false
    }

    private val mockedRole = UserRole().apply {
        id = 2
        name = "CREW_MANAGER"
    }

    private val mockedUser = User().apply {
        id = 1;
        name = "name"
        surname = "surname"
        nick = "nick"
        password = "12345"
        birthDate = LocalDate.now()
        registrationDate = LocalDate.now()
        userType = mockedRole
        email = "test@mail.ru"
        phone = "8-912-345-67-89"
        isShareContactInfo = true
        isVip = true
        isActivated = true
        isPirate = false
    }

    private val mockedShip = Ship().apply {
        id = 1
        owner = mockedUser
        name = "name"
        speed = 10
        capacity = 10
        fuelConsumption = 10
        length = 10
        width = 10
        pricePerDay = 1000
        ratesNumber = 1
        ratesAverage = 5.0
        photo = "hash"
        description = "test"
    }

    private val mockedCrew = Crew().apply {
        id = 1
        teamName = "team"
        crewOwner = mockedUser
        pricePerDay = 1000
        ratesNumber = 1
        ratesAverage = 5.0
        photo = "hash"
        description = "test"
    }

    private val mockedTripRequest = TripRequest().apply {
        id = 1
        traveler = mockedUser
        dateStart = LocalDate.now()
        dateEnd = LocalDate.now()
        islandStart = mockedIsland
        islandEnd = mockedIsland
        status = TripRequestStatus.APPROVED_BY_BOTH
        crew = mockedCrew
        ship = mockedShip
        cost = 1000
        isRated = false
    }

    private val mockedTripRatingRequest = TripRatingRequest().apply {
        tripId = 1
        ship = 3.0
        crew = 3.0
    }
}