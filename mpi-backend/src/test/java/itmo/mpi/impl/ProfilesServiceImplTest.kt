package itmo.mpi.impl

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import itmo.mpi.repository.CrewMemberRepository
import itmo.mpi.repository.CrewRepository
import itmo.mpi.repository.ShipRepository
import itmo.mpi.repository.UserRepository
import itmo.mpi.utils.CommonUtils
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ProfilesServiceImplTest {

    @MockK
    private lateinit var userRepository: UserRepository

    @MockK
    private lateinit var crewRepository: CrewRepository

    @MockK
    private lateinit var shipRepository: ShipRepository

    @MockK
    private lateinit var crewMemberRepository: CrewMemberRepository

    @MockK
    private lateinit var commonUtils: CommonUtils

    @InjectMockKs
    private lateinit var profileServiceImpl: ProfilesServiceImpl

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }
}