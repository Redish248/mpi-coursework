package itmo.mpi.impl

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import itmo.mpi.repository.CrewRepository
import itmo.mpi.repository.ShipRepository
import itmo.mpi.repository.TripRequestRepository
import itmo.mpi.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith

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
    }
}