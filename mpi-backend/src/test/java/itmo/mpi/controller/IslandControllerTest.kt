package itmo.mpi.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.junit5.MockKExtension
import itmo.mpi.entity.Island
import itmo.mpi.impl.IslandServiceImpl
import org.assertj.core.util.Lists
import org.hamcrest.CoreMatchers
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
@WebMvcTest(IslandController::class)
@AutoConfigureMockMvc(addFilters = false)
class IslandControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var islandServiceImpl: IslandServiceImpl

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        every { islandServiceImpl.getIslands() } returns Lists.newArrayList(mockedIsland)
    }

    @Test
    fun `Test endpoint with getting islands`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/island"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.`is`("Kater")))
    }

    private val mockedIsland = Island().apply {
        name = "Kater"
    }
}