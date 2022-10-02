package itmo.mpi.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.junit5.MockKExtension
import itmo.mpi.entity.Crew
import itmo.mpi.entity.CrewMember
import itmo.mpi.entity.Ship
import itmo.mpi.model.profiles.CrewResponse
import itmo.mpi.model.profiles.ShipResponse
import itmo.mpi.model.profiles.UserProfileResponse
import itmo.mpi.service.ProfilesService
import itmo.mpi.utils.CommonUtils
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(MockKExtension::class)
@AutoConfigureTestDatabase
@WebMvcTest(ProfilesController::class)
@AutoConfigureMockMvc(addFilters = false)
class ProfilesControllerTest(@Autowired val mockMcv: MockMvc) {

    @MockkBean
    private lateinit var profilesService: ProfilesService

    @MockkBean
    private lateinit var commonUtils: CommonUtils

    @BeforeEach
    fun init() {
        MockKAnnotations.init(this)
        every { commonUtils.currentUser } returns TestingAuthenticationToken(Object(), Object(), "test")
    }

    @Test
    fun `check current profile available`() {
        every { profilesService.getCurrentUserProfile(any()) } returns UserProfileResponse(
            /* uid = */ 1,
            /* name = */ "",
            /* surname = */ "",
            /* nick = */ "",
            /* birthDate = */ "",
            /* email = */ "",
            /* phone = */ "",
            /* shareContactInfo = */ false,
            /* isVip = */ false,
            /* isActivated = */ false
        )

        mockMcv
            .perform(MockMvcRequestBuilders.get(baseUrl))
            .andExpect(MockMvcResultMatchers.status().isOk)

    }

    @Test
    fun `check ship is available`() {
        every { profilesService.getUserShip(any()) } returns ShipResponse(ship)

        mockMcv
            .perform(MockMvcRequestBuilders.get("$baseUrl/ship"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `check crew is available`() {
        every { profilesService.getUserCrew(any()) } returns CrewResponse(crew, mutableListOf<CrewMember>())

        mockMcv
            .perform(MockMvcRequestBuilders.get("$baseUrl/crew"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    private val crew = Crew().apply {
        id = 123
        teamName = ""
        ratesAverage = 4.0
        photo = ""
        description = ""
        pricePerDay = 40
    }
    private val ship = Ship().apply {
        id = 123
        name = ""
        speed = 40
        capacity = 10
        fuelConsumption = 12
        width = 10
        length = 10
        pricePerDay = 10
        photo = ""
        description = ""
        ratesAverage = 1.0
    }
    private val baseUrl: String = "/mpi/profiles"
}