package itmo.mpi.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.junit5.MockKExtension
import itmo.mpi.entity.Admin
import itmo.mpi.entity.User
import itmo.mpi.entity.UserRole
import itmo.mpi.model.UserInfo
import itmo.mpi.service.AdminService
import itmo.mpi.service.UserService
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
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
@AutoConfigureTestDatabase
@WebMvcTest(RegistrationController::class)
@AutoConfigureMockMvc(addFilters = false)
class RegistrationControllerTest(@Autowired val mockMvc: MockMvc, @Autowired val objectMapper: ObjectMapper) {

    @MockkBean
    private lateinit var userService: UserService

    @MockkBean
    private lateinit var commonUtils: CommonUtils

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        every { userService.createUser(any()) } returns mockedUser
        every { commonUtils.getCurrentUser() } returns TestingAuthenticationToken(Object(), Object(), "test")
    }

    @Test
    fun `Check that user registration endpoint is available`() {
        val userInfo = UserInfo().apply {
            email = "tst@ya.ru"
            name = "test-name"
            surname = "test-surname"
            nick = "nick"
            birthDate = LocalDate.now().toString()
            userType = "TRAVELER"
            phone = "8-912-345-67-89"
        }

        mockMvc.perform(MockMvcRequestBuilders.post("/mpi/signup/registerUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userInfo)))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.`is`(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.`is`("tst@ya.ru")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.`is`("test-name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname", CoreMatchers.`is`("test-surname")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone", CoreMatchers.`is`("8-912-345-67-89")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userType.id", CoreMatchers.`is`(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userType.name", CoreMatchers.`is`("TRAVELER")))
    }



    @Test
    fun `Check that roles endpoint is available`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/mpi/signup/roles"))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

    private val mockedUser = User().apply {
        val role = UserRole()
        role.id = 1
        role.name = "TRAVELER"

        id = 1;
        email = "tst@ya.ru"
        name = "test-name"
        surname = "test-surname"
        nick = "nick"
        birthDate = LocalDate.now()
        userType = role
        phone = "8-912-345-67-89"
    }
}