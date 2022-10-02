package itmo.mpi.impl

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import itmo.mpi.dto.TripRequestDto
import itmo.mpi.entity.*
import itmo.mpi.repository.CrewRepository
import itmo.mpi.repository.IslandRepository
import itmo.mpi.repository.ShipRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class OptionsLookUpServiceImplTest {

    @MockK
    private lateinit var crewRepository: CrewRepository

    @MockK
    private lateinit var shipRepository: ShipRepository

    @MockK
    private lateinit var islandRepository: IslandRepository

    @InjectMockKs
    private lateinit var optionsLookUpServiceImpl: OptionsLookUpServiceImpl

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Test creating trip request option`() {
        every { crewRepository.getFreeCrewsForDates(any(), any()) } returns listOf(mockedCrew)
        every { shipRepository.getFreeShipsForTrip(any(), any()) } returns listOf(mockedShip)
        every { islandRepository.getById(1) } returns mockedIslandStart
        every { islandRepository.getById(2) } returns mockedIslandEnd

        val result = optionsLookUpServiceImpl.lookUpOptions(mockedTripRequestDto)

        assertAll(
                { assertEquals(mockedShip, result[0].ship) },
                { assertEquals(mockedCrew, result[0].crew) },
                { assertEquals(2010, result[0].price) },
                { assertEquals(LocalDate.now().plusDays(2), result[0].startDate) },
                { assertEquals(LocalDate.now().plusDays(3), result[0].finishDate) }
        )
    }

    private val mockedIslandStart = Island().apply {
        id = 1
        name = "name1"
        xCoordinate = 1
        yCoordinate = 1
        hasPirates = false
    }

    private val mockedIslandEnd = Island().apply {
        id = 2
        name = "name2"
        xCoordinate = 3
        yCoordinate = 3
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

    private val mockedTripRequestDto =
            TripRequestDto.builder()
                    .from(mockedIslandStart)
                    .to(mockedIslandEnd)
                    .budget(1000000)
                    .startDate(LocalDate.now().plusDays(2))
                    .build()

}