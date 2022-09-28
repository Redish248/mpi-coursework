package itmo.mpi.impl

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import itmo.mpi.entity.User
import itmo.mpi.entity.UserRole
import itmo.mpi.repository.AdminRepository
import itmo.mpi.repository.UserRepository
import itmo.mpi.repository.UserRoleRepository
import itmo.mpi.service.UserService
import org.assertj.core.util.Lists
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class UserServiceImplTest {

    @MockK
    lateinit var userRepository: UserRepository

    @MockK
    lateinit var userRoleRepository: UserRoleRepository

    @MockK
    lateinit var adminRepository: AdminRepository

    @InjectMockKs
    private lateinit var userServiceImpl: UserServiceImpl

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        every { userRepository.findAllByRegistrationDateBefore(any()) } returns Lists.newArrayList(mockedUser)
        every { userRepository.deleteAll(any()) } answers {nothing}
    }

    @Test
    fun `Test removing old registration requests`() {
        userServiceImpl.removeOldRegistrationRequests(LocalDate.now());
        verify { userRepository.findAllByRegistrationDateBefore(LocalDate.now().minusDays(7))}
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