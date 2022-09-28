package itmo.mpi.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.junit5.MockKExtension
import itmo.mpi.entity.User
import itmo.mpi.entity.UserRole
import itmo.mpi.service.AdminService
import itmo.mpi.service.UserService
import itmo.mpi.utils.CommonUtils
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
@AutoConfigureTestDatabase
@WebMvcTest(RegistrationController::class)
class RegistrationControllerTest(@Autowired val mockMvc: MockMvc)  {

    @MockkBean
    lateinit var userService: UserService

    @MockkBean
    lateinit var adminService: AdminService

    @MockkBean
    lateinit var commonUtils: CommonUtils

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        every { userService.createUser(any()) } returns mockedUser
    }

    @Test
    fun `Check that user registration endpoint is available`() {
        mockMvc.post("/mpi/signup/registerUser") {
            param("value", "test value")
        }.andExpect {
            status { isOk() }
            content {
                string("test value")
            }
        }
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
        password = "12345"
        birthDate = LocalDate.now()
        userType = role
        email = "test@mail.ru"
        phone = "8-912-345-67-89"
    }
}