package itmo.mpi.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verify
import itmo.mpi.service.FsbAgentService
import itmo.mpi.service.IslandService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(MockKExtension::class)
@AutoConfigureTestDatabase
@WebMvcTest(FsbController::class)
@AutoConfigureMockMvc(addFilters = false)
class FbsControllerTest(@Autowired val mockMcv: MockMvc) {

    @MockkBean
    private lateinit var fsbAgentService: FsbAgentService

    @MockkBean
    private lateinit var islandService: IslandService


    @BeforeEach
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `check get crews is available`() {
        every { fsbAgentService.getCrews() } returns emptyList()
        mockMcv
            .perform(MockMvcRequestBuilders.get("$baseUrl/crews"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `check get islands is available`() {
        every { islandService.allIslandsForFsb } returns emptyList()
        mockMcv
            .perform(MockMvcRequestBuilders.get("$baseUrl/island"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `check mark crew as good person`() {
        val id = 3L
        justRun { fsbAgentService.markAsGoodPerson(any()) }
        mockMcv
            .perform(MockMvcRequestBuilders.put("$baseUrl/crew/$id/pirate/false"))
            .andExpect(MockMvcResultMatchers.status().isOk)

        verify(exactly = 1) { fsbAgentService.markAsGoodPerson(id) }
    }

    @Test
    fun `check mark crew as pirate`() {
        val id = 3L
        justRun { fsbAgentService.markAsPirate(any()) }
        mockMcv
            .perform(MockMvcRequestBuilders.put("$baseUrl/crew/$id/pirate/true"))
            .andExpect(MockMvcResultMatchers.status().isOk)

        verify(exactly = 1) { fsbAgentService.markAsPirate(id) }
    }

    @Test
    fun `check mark island as dangerous`() {
        val id = 3
        justRun { fsbAgentService.markIslandAsDangerous(any()) }
        mockMcv
            .perform(MockMvcRequestBuilders.put("$baseUrl/island/$id/pirate/true"))
            .andExpect(MockMvcResultMatchers.status().isOk)

        verify(exactly = 1) { fsbAgentService.markIslandAsDangerous(id) }
    }

    @Test
    fun `check mark island as safe`() {
        val id = 3
        justRun { fsbAgentService.markIslandAsSafe(any()) }
        mockMcv
            .perform(MockMvcRequestBuilders.put("$baseUrl/island/$id/pirate/false"))
            .andExpect(MockMvcResultMatchers.status().isOk)

        verify(exactly = 1) { fsbAgentService.markIslandAsSafe(id) }
    }

    private val baseUrl = "/mpi/fsb"

}