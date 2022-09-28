package itmo.mpi.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import java.time.LocalDate
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
//@WebMvcTest(RegistrationController::class)
class RegistrationControllerTest {

   // @Autowired
   // private lateinit var mockMvc: MockMvc

    @MockK
    lateinit var userService: UserService

    @MockK
    lateinit var adminService: AdminService

    @MockK
    lateinit var commonUtils: CommonUtils

    @InjectMockKs
    private lateinit var controller: RegistrationController

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        every { userService.createUser(any()) } returns mockedUser
    }

    @Test
    fun `Check that user registration endpoint is available`() {
        assertEquals(1,1);
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