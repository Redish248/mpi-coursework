package itmo.mpi.impl

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import itmo.mpi.entity.*
import itmo.mpi.repository.CrewRepository
import itmo.mpi.repository.ShipRepository
import itmo.mpi.repository.TripRequestRepository
import itmo.mpi.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class TripRequestInfoServiceImplTest {

    @MockK
    private lateinit var tripRequestRepository: TripRequestRepository

    @MockK
    private lateinit var userRepository: UserRepository

    @MockK
    private lateinit var crewRepository: CrewRepository

    @MockK
    private lateinit var shipRepository: ShipRepository

    @InjectMockKs
    private lateinit var tripRequestInfoServiceImpl: TripRequestInfoServiceImpl

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        every { tripRequestRepository.findByShipAndStatusIn(any(), any()) } returns listOf(mockedTripRequest)
        every { tripRequestRepository.findByCrewAndStatusIn(any(), any()) } returns listOf(mockedTripRequest)
        every { tripRequestRepository.findByShipInAndStatusIn(any(), any()) } returns listOf(mockedTripRequest)
        every { tripRequestRepository.findByCrewInAndStatusIn(any(), any()) } returns listOf(mockedTripRequest)
        every { tripRequestRepository.findByTravelerAndStatusIn(any(), any()) } returns listOf(mockedTripRequest)
        every { tripRequestRepository.findAllByStatus(any()) } returns listOf(mockedTripRequest)
        every { shipRepository.findByOwner(any()) } returns listOf(mockedShip)
        every { crewRepository.findByCrewOwner(any()) } returns listOf(mockedCrew)
    }

    @Test
    fun `Test getting pending requests for ship`() {
        val result = tripRequestInfoServiceImpl.getPendingRequestsForShip(mockedShip)[0]
        assertAll(
                    { assertEquals(mockedTripRequest, result) },
                    { assertEquals("", result.traveler.password) },
                    { assertEquals("", result.crew.crewOwner.password) },
                )
    }

    @Test
    fun `Test getting pending requests for crew`() {
        val result = tripRequestInfoServiceImpl.getPendingRequestsForCrew(mockedCrew)[0]
        assertAll(
                { assertEquals(mockedTripRequest, result) },
                { assertEquals("", result.traveler.password) },
                { assertEquals("", result.crew.crewOwner.password) },
        )
    }

    @Test
    fun `Test getting complete requests for ship`() {
        val result = tripRequestInfoServiceImpl.getCompleteRequestsForShip(mockedShip)[0]
        assertAll(
                { assertEquals(mockedTripRequest, result) },
                { assertEquals("", result.traveler.password) },
                { assertEquals("", result.crew.crewOwner.password) },
        )
    }

    @Test
    fun `Test getting complete requests for crew`() {
        val result = tripRequestInfoServiceImpl.getCompleteRequestsForCrew(mockedCrew)[0]
        assertAll(
                { assertEquals(mockedTripRequest, result) },
                { assertEquals("", result.traveler.password) },
                { assertEquals("", result.crew.crewOwner.password) },
        )
    }

    @Test
    fun `Test getting pending trips for user - TRAVELLER_ROLE`() {
        every { userRepository.findByNick(any()) } returns mockedUser

        val result = tripRequestInfoServiceImpl.getPendingRequestsForUser("nick")[0]
        assertAll(
                { assertEquals(mockedTripRequest, result) },
                { assertEquals("", result.traveler.password) },
                { assertEquals("", result.crew.crewOwner.password) }
        )
    }

    @Test
    fun `Test getting pending trips for user - CREW_ROLE`() {
        mockedUser.userType.name = "CREW_MANAGER"
        every { userRepository.findByNick(any()) } returns mockedUser

        val result = tripRequestInfoServiceImpl.getPendingRequestsForUser("nick")[0]
        assertAll(
                { assertEquals(mockedTripRequest, result) },
                { assertEquals("", result.traveler.password) },
                { assertEquals("", result.crew.crewOwner.password) }
        )

    }

    @Test
    fun `Test getting pending trips for user - SHIP_ROLE`() {
        mockedUser.userType.name = "SHIP_OWNER"
        every { userRepository.findByNick(any()) } returns mockedUser

        val result = tripRequestInfoServiceImpl.getPendingRequestsForUser("nick")[0]
        assertAll(
                { assertEquals(mockedTripRequest, result) },
                { assertEquals("", result.traveler.password) },
                { assertEquals("", result.crew.crewOwner.password) }
        )
    }

    @Test
    fun `Test getting pending trips for user - incorrect role`() {
        mockedUser.userType.name = "test"
        every { userRepository.findByNick(any()) } returns mockedUser

        assertThrows<IllegalArgumentException> { tripRequestInfoServiceImpl.getPendingRequestsForUser("nick") }
    }

    @Test
    fun `Test getting complete trips for user - TRAVELLER_ROLE`() {
        every { userRepository.findByNick(any()) } returns mockedUser

        val result = tripRequestInfoServiceImpl.getCompleteRequestsForUser("nick")[0]
        assertAll(
                { assertEquals(mockedTripRequest, result) },
                { assertEquals("", result.traveler.password) },
                { assertEquals("", result.crew.crewOwner.password) }
        )
    }

    @Test
    fun `Test getting complete trips for user - CREW_ROLE`() {
        mockedUser.userType.name = "CREW_MANAGER"
        every { userRepository.findByNick(any()) } returns mockedUser

        val result = tripRequestInfoServiceImpl.getCompleteRequestsForUser("nick")[0]
        assertAll(
                { assertEquals(mockedTripRequest, result) },
                { assertEquals("", result.traveler.password) },
                { assertEquals("", result.crew.crewOwner.password) }
        )

    }

    @Test
    fun `Test getting complete trips for user - SHIP_ROLE`() {
        mockedUser.userType.name = "SHIP_OWNER"
        every { userRepository.findByNick(any()) } returns mockedUser

        val result = tripRequestInfoServiceImpl.getCompleteRequestsForUser("nick")[0]
        assertAll(
                { assertEquals(mockedTripRequest, result) },
                { assertEquals("", result.traveler.password) },
                { assertEquals("", result.crew.crewOwner.password) }
        )
    }

    @Test
    fun `Test getting complete trips for user - incorrect role`() {
        mockedUser.userType.name = "test"
        every { userRepository.findByNick(any()) } returns mockedUser

        assertThrows<IllegalArgumentException> { tripRequestInfoServiceImpl.getCompleteRequestsForUser("nick") }
    }

    @Test
    fun `Test getting ended trips for user - TRAVELLER_ROLE`() {
        every { userRepository.findByNick(any()) } returns mockedUser

        val result = tripRequestInfoServiceImpl.getEndedRequestsForUser("nick")[0]
        assertAll(
                { assertEquals(mockedTripRequest, result) },
                { assertEquals("", result.traveler.password) },
                { assertEquals("", result.crew.crewOwner.password) }
        )
    }

    @Test
    fun `Test getting ended trips for user - CREW_ROLE`() {
        mockedUser.userType.name = "CREW_MANAGER"
        every { userRepository.findByNick(any()) } returns mockedUser

        val result = tripRequestInfoServiceImpl.getEndedRequestsForUser("nick")[0]
        assertAll(
                { assertEquals(mockedTripRequest, result) },
                { assertEquals("", result.traveler.password) },
                { assertEquals("", result.crew.crewOwner.password) }
        )

    }

    @Test
    fun `Test getting ended trips for user - SHIP_ROLE`() {
        mockedUser.userType.name = "SHIP_OWNER"
        every { userRepository.findByNick(any()) } returns mockedUser

        val result = tripRequestInfoServiceImpl.getEndedRequestsForUser("nick")[0]
        assertAll(
                { assertEquals(mockedTripRequest, result) },
                { assertEquals("", result.traveler.password) },
                { assertEquals("", result.crew.crewOwner.password) }
        )
    }

    @Test
    fun `Test getting ended trips for user - incorrect role`() {
        mockedUser.userType.name = "test"
        every { userRepository.findByNick(any()) } returns mockedUser

        assertThrows<IllegalArgumentException> { tripRequestInfoServiceImpl.getEndedRequestsForUser("nick") }
    }

    @Test
    fun `Test getting cancelled trips for user - TRAVELLER_ROLE`() {
        every { userRepository.findByNick(any()) } returns mockedUser

        val result = tripRequestInfoServiceImpl.getCancelledRequestsForUser("nick")[0]
        assertAll(
                { assertEquals(mockedTripRequest, result) },
                { assertEquals("", result.traveler.password) },
                { assertEquals("", result.crew.crewOwner.password) }
        )
    }

    @Test
    fun `Test getting cancelled trips for user - CREW_ROLE`() {
        mockedUser.userType.name = "CREW_MANAGER"
        every { userRepository.findByNick(any()) } returns mockedUser

        val result = tripRequestInfoServiceImpl.getCancelledRequestsForUser("nick")[0]
        assertAll(
                { assertEquals(mockedTripRequest, result) },
                { assertEquals("", result.traveler.password) },
                { assertEquals("", result.crew.crewOwner.password) }
        )

    }

    @Test
    fun `Test getting cancelled trips for user - SHIP_ROLE`() {
        mockedUser.userType.name = "SHIP_OWNER"
        every { userRepository.findByNick(any()) } returns mockedUser

        val result = tripRequestInfoServiceImpl.getCancelledRequestsForUser("nick")[0]
        assertAll(
                { assertEquals(mockedTripRequest, result) },
                { assertEquals("", result.traveler.password) },
                { assertEquals("", result.crew.crewOwner.password) }
        )
    }

    @Test
    fun `Test getting approved requests for ship`() {
        val result = tripRequestInfoServiceImpl.getApprovedRequestsForShip(mockedShip)[0]
        assertAll(
                { assertEquals(mockedTripRequest, result) },
                { assertEquals("", result.traveler.password) },
                { assertEquals("", result.crew.crewOwner.password) },
        )
    }

    @Test
    fun `Test getting approved requests for crew`() {
        val result = tripRequestInfoServiceImpl.getApprovedRequestsForCrew(mockedCrew)[0]
        assertAll(
                { assertEquals(mockedTripRequest, result) },
                { assertEquals("", result.traveler.password) },
                { assertEquals("", result.crew.crewOwner.password) },
        )
    }

    @Test
    fun `Test getting trips by status`() {
        val result = tripRequestInfoServiceImpl.getTripsByStatus(TripRequestStatus.COMPLETE)[0]
        assertAll(
                { assertEquals(mockedTripRequest, result) },
                { assertEquals("", result.traveler.password) },
                { assertEquals("", result.crew.crewOwner.password) },
        )
    }

    @Test
    fun `Test getting cancelled trips for user - incorrect role`() {
        mockedUser.userType.name = "test"
        every { userRepository.findByNick(any()) } returns mockedUser

        assertThrows<IllegalArgumentException> { tripRequestInfoServiceImpl.getCancelledRequestsForUser("nick") }
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
        name = "TRAVELER"
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

}