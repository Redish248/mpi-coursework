package itmo.mpi.controller

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import itmo.mpi.entity.User
import itmo.mpi.service.AdminService
import itmo.mpi.service.UserService
import itmo.mpi.utils.CommonUtils
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class RegistrationControllerTest {

    @MockK
    lateinit var userService: UserService

    @MockK
    lateinit var adminService: AdminService

    @MockK
    lateinit var commonUtils: CommonUtils

    @InjectMockKs
    var controller = RegistrationController(userService, adminService, commonUtils)

    @Before
    fun setUp() {
        every { userService.createUser(any()) } returns mockedUser()
    }

    @Test
    fun testRegistration() {

    }

    private fun mockedUser(): User {
        val user = User();
        user.id = 1;
        user.email = "tst@ya.ru"
        user.name = "ira"
        return user;
    }
}