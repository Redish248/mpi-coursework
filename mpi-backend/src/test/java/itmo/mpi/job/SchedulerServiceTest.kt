package itmo.mpi.job

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verify
import itmo.mpi.entity.TripRequest
import itmo.mpi.impl.TripRequestInfoServiceImpl
import itmo.mpi.impl.TripRequestManipulationServiceImpl
import itmo.mpi.impl.UserServiceImpl
import org.assertj.core.util.Lists
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class SchedulerServiceTest {

    @MockK
    lateinit var userServiceImpl: UserServiceImpl

    @MockK
    lateinit var tripRequestManipulationServiceImpl: TripRequestManipulationServiceImpl

    @MockK
    lateinit var tripRequestInfoServiceImpl: TripRequestInfoServiceImpl

    @InjectMockKs
    private lateinit var schedulerService: SchedulerService

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        justRun { userServiceImpl.removeOldRegistrationRequests(any()) }
        every { tripRequestInfoServiceImpl.getTripsByStatus(any()) } answers { Lists.newArrayList(mockedTripRequest) }
        justRun { tripRequestManipulationServiceImpl.endTrip(any()) }
    }

    @Test
    fun `Test cleaning registration requests`() {
        schedulerService.cleanRegistrationRequests()

        verify { userServiceImpl.removeOldRegistrationRequests(any()) }
    }

    @Test
    fun `Test cleaning old trips by date`() {
        schedulerService.endRequestsByDate()

        verify { tripRequestInfoServiceImpl.getTripsByStatus(any()) }
        verify { tripRequestManipulationServiceImpl.endTrip(mockedTripRequest) }
    }

    private val mockedTripRequest = TripRequest().apply {
        dateEnd = LocalDate.now().minusDays(15)
    }
}