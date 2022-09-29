package itmo.mpi.impl

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import itmo.mpi.repository.CrewRepository
import itmo.mpi.repository.IslandRepository
import itmo.mpi.repository.ShipRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith

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
}