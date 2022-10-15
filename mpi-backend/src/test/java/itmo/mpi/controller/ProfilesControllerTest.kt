package itmo.mpi.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.junit5.MockKExtension
import itmo.mpi.entity.Crew
import itmo.mpi.entity.CrewMember
import itmo.mpi.entity.Ship
import itmo.mpi.entity.User
import itmo.mpi.model.UserInfoUpdate
import itmo.mpi.model.profiles.*
import itmo.mpi.service.ProfilesService
import itmo.mpi.utils.CommonUtils
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(MockKExtension::class)
@AutoConfigureTestDatabase
@WebMvcTest(ProfilesController::class)
@AutoConfigureMockMvc(addFilters = false)
class ProfilesControllerTest(@Autowired val mockMvc: MockMvc, @Autowired val objectMapper: ObjectMapper) {

    @MockkBean
    private lateinit var profilesService: ProfilesService

    @MockkBean
    private lateinit var commonUtils: CommonUtils

    private val baseUrl: String = "/mpi/profiles"

    @BeforeEach
    fun init() {
        MockKAnnotations.init(this)
        every { commonUtils.currentUser } returns TestingAuthenticationToken(Object(), Object(), "test")
    }

    @Test
    fun `Check current profile available`() {
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

        mockMvc
            .perform(MockMvcRequestBuilders.get(baseUrl))
            .andExpect(MockMvcResultMatchers.status().isOk)

    }

    @Test
    fun `Check ship is available`() {
        every { profilesService.getUserShip(any()) } returns ShipResponse(ship)

        mockMvc
            .perform(MockMvcRequestBuilders.get("$baseUrl/ship"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `Check crew is available`() {
        every { profilesService.getUserCrew(any()) } returns CrewResponse(crew, mutableListOf<CrewMember>())

        mockMvc
            .perform(MockMvcRequestBuilders.get("$baseUrl/crew"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `Check ship profiles is available`() {
        val expected: ShipProfileResponse = ShipProfileResponse().apply {
            name = "test"
        }
        every { profilesService.getShipsForCurrentUser(any()) } returns listOf(expected)

        mockMvc
                .perform(MockMvcRequestBuilders.get("$baseUrl/ships"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.`is`("test")))
    }

    @Test
    fun `Check crew profiles is available`() {
        val expected: CrewProfileResponse = CrewProfileResponse().apply {
            name = "test"
        }
        every { profilesService.getCrewsForCurrentUser(any()) } returns listOf(expected)

        mockMvc
                .perform(MockMvcRequestBuilders.get("$baseUrl/crews"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.`is`("test")))
    }

    @Test
    fun `Check that ship can be registered`() {
        val expected: ShipResponse = ShipResponse().apply {
            title = "test"
        }
        every { profilesService.registerShip(any(), any()) } returns expected

        mockMvc
                .perform(MockMvcRequestBuilders.post("$baseUrl/ship")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ShipRequest())))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.`is`("test")))
    }

    @Test
    fun `Check that crew can be registered`() {
        val expected: CrewResponse = CrewResponse().apply {
            teamName = "test"
        }
        every { profilesService.registerCrew(any(), any()) } returns expected

        mockMvc
                .perform(MockMvcRequestBuilders.post("$baseUrl/crew")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(RegisterCrewRequest())))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.teamName", CoreMatchers.`is`("test")))
    }

    @Test
    fun `Check that ship can be updated`() {
        val expected: ShipResponse = ShipResponse().apply {
            title = "test"
        }
        every { profilesService.updateShip(any(), any()) } returns expected

        mockMvc
                .perform(MockMvcRequestBuilders.post("$baseUrl/shipinfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ShipRequest())))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.`is`("test")))
    }

    @Test
    fun `Check that crew can be updated`() {
        val expected: CrewResponse = CrewResponse().apply {
            teamName = "test"
        }
        every { profilesService.updateCrew(any(), any()) } returns expected

        mockMvc
                .perform(MockMvcRequestBuilders.post("$baseUrl/crewinfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(RegisterCrewRequest())))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.teamName", CoreMatchers.`is`("test")))
    }

    @Test
    fun `Test updating user info`() {
        val expected: User = User().apply {
            nick = "test"
        }
        every { profilesService.updateUser(any()) } returns expected

        mockMvc
                .perform(MockMvcRequestBuilders.post("$baseUrl/userinfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UserInfoUpdate())))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.nick", CoreMatchers.`is`("test")))
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
}