package itmo.mpi.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import itmo.mpi.dto.TripOption
import itmo.mpi.dto.TripRequestDto
import itmo.mpi.entity.Crew
import itmo.mpi.entity.Ship
import itmo.mpi.entity.TripRequest
import itmo.mpi.impl.OptionsLookUpServiceImpl
import itmo.mpi.impl.TripRequestInfoServiceImpl
import itmo.mpi.impl.TripRequestManipulationServiceImpl
import org.assertj.core.util.Lists
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(MockKExtension::class)
@AutoConfigureTestDatabase
@WebMvcTest(TripRequestsController::class)
@AutoConfigureMockMvc(addFilters = false)
class TripRequestsControllerTest(@Autowired val mockMvc: MockMvc, @Autowired val objectMapper: ObjectMapper) {
    @MockkBean
    lateinit var optionsLookUpServiceImpl: OptionsLookUpServiceImpl

    @MockkBean
    lateinit var tripRequestManipulationServiceImpl: TripRequestManipulationServiceImpl

    @MockkBean
    lateinit var tripRequestInfoServiceImpl: TripRequestInfoServiceImpl

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        every { optionsLookUpServiceImpl.lookUpOptions(any()) } returns Lists.newArrayList(mockedTripOption)
        every { tripRequestManipulationServiceImpl.createTripRequest(any(), any()) } answers { nothing }
        every { tripRequestManipulationServiceImpl.cancelRequest(any(), any()) } answers { nothing }
        every { tripRequestManipulationServiceImpl.rejectRequest(any(), any()) } answers { nothing }
        every { tripRequestManipulationServiceImpl.approveRequest(any(), any()) } answers { nothing }
        every { tripRequestManipulationServiceImpl.deleteRequest(any(), any()) } answers { nothing }
        every { tripRequestManipulationServiceImpl.rateTrip(any()) } answers { nothing }
        every { tripRequestManipulationServiceImpl.endTrip(any()) } answers { nothing }

        every { tripRequestInfoServiceImpl.getCompleteRequestsForUser(any()) } answers { Lists.newArrayList(mockedTripRequest) }
        every { tripRequestInfoServiceImpl.getPendingRequestsForUser(any()) } answers { Lists.newArrayList(mockedTripRequest) }
        every { tripRequestInfoServiceImpl.getCancelledRequestsForUser(any()) } answers { Lists.newArrayList(mockedTripRequest) }
        every { tripRequestInfoServiceImpl.getEndedRequestsForUser(any()) } answers { Lists.newArrayList(mockedTripRequest) }

        every { tripRequestInfoServiceImpl.getPendingRequestsForShip(any()) } answers { Lists.newArrayList(mockedTripRequest) }
        every { tripRequestInfoServiceImpl.getCompleteRequestsForShip(any()) } answers { Lists.newArrayList(mockedTripRequest) }
        every { tripRequestInfoServiceImpl.getPendingRequestsForCrew(any()) } answers { Lists.newArrayList(mockedTripRequest) }
        every { tripRequestInfoServiceImpl.getCompleteRequestsForCrew(any()) } answers { Lists.newArrayList(mockedTripRequest) }


        val authentication: Authentication = mockk()
        val securityContext: SecurityContext = mockk()
        every { securityContext.authentication } returns authentication
        every { authentication.name } returns "nick"
        SecurityContextHolder.setContext(securityContext)
    }

    @Test
    fun `Test getting expedition options`() {
        mockMvc.perform(MockMvcRequestBuilders.post("/request/options")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TripRequestDto.builder().build())))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price", CoreMatchers.`is`(1000)))
    }

    @Test
    fun `Test trip requests creating`() {
        mockMvc.perform(MockMvcRequestBuilders.post("/request/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockedTripRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `Test trip requests approving`() {
        mockMvc.perform(MockMvcRequestBuilders.post("/request/approve")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockedTripRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `Test trip requests cancelling`() {
        mockMvc.perform(MockMvcRequestBuilders.post("/request/cancel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockedTripRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `Test getting all completed trips`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/request/complete"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cost", CoreMatchers.`is`(10000)))
    }

    @Test
    fun `Test getting all pending trips`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/request/pending"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cost", CoreMatchers.`is`(10000)))
    }

    @Test
    fun `Test getting all cancelled trips`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/request/cancelled"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cost", CoreMatchers.`is`(10000)))
    }

    @Test
    fun `Test getting all ended trips`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/request/ended"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cost", CoreMatchers.`is`(10000)))
    }

    @Test
    fun `Test getting all pending requests for ship`() {
        mockMvc.perform(MockMvcRequestBuilders.post("/request/ship/pending")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Ship())))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cost", CoreMatchers.`is`(10000)))
    }

    @Test
    fun `Test getting all completed requests for ship`() {
        mockMvc.perform(MockMvcRequestBuilders.post("/request/ship/complete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Ship())))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cost", CoreMatchers.`is`(10000)))
    }

    @Test
    fun `Test getting all pending requests for crew`() {
        mockMvc.perform(MockMvcRequestBuilders.post("/request/crew/pending")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Crew())))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cost", CoreMatchers.`is`(10000)))
    }

    @Test
    fun `Test getting all completed requests for crew`() {
        mockMvc.perform(MockMvcRequestBuilders.post("/request/crew/complete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Crew())))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cost", CoreMatchers.`is`(10000)))
    }

    @Test
    fun `Test deleting all requests for user`() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockedTripRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `Test rating trip`() {
        mockMvc.perform(MockMvcRequestBuilders.post("/request/crew/rate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockedTripRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `Test ending trip`() {
        mockMvc.perform(MockMvcRequestBuilders.post("/request/end")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockedTripRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

    private val mockedTripRequest = TripRequest().apply {
        cost = 10000
    }

    private val mockedTripOption = TripOption.builder().price(1000).build()
}