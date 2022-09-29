package itmo.mpi.impl

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import itmo.mpi.entity.Admin
import itmo.mpi.entity.User
import itmo.mpi.entity.UserRole
import itmo.mpi.exception.UserAlreadyExistException
import itmo.mpi.model.UserInfo
import itmo.mpi.repository.AdminRepository
import itmo.mpi.repository.UserRepository
import itmo.mpi.repository.UserRoleRepository
import org.assertj.core.util.Lists
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.test.assertNotNull
import kotlin.test.assertNull

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
        every { userRepository.deleteAll(any()) } answers { nothing }
        every { userRoleRepository.findUserRoleByName(any()) } answers { mockedRole }
        every { userRepository.findUsersByIsActivated(any()) }  returns Lists.newArrayList(mockedUser)
    }

    @Test
    fun `Test with finding all not activated users`() {
        val result = userServiceImpl.findAllNotActivatedUsers();
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        assertAll(
                { assertEquals(mockedUser.name, result[0].name) },
                { assertEquals(mockedUser.surname, result[0].surname) },
                { assertEquals(mockedUser.nick, result[0].nick) },
                { assertNull(result[0].password) },
                { assertEquals(mockedUser.birthDate.format(formatter), result[0].birthDate) },
                { assertEquals(mockedRole.name, result[0].userType) },
                { assertEquals(mockedUser.email, result[0].email) },
                { assertEquals(mockedUser.phone, result[0].phone) },
                { assertNotNull(LocalDate.now().format(formatter), result[0].registrationDate) },
        )
    }

    @Test
    fun `Test creating user`() {
        every { adminRepository.findAdminByNick(any()) } answers { null }
        every { userRepository.findByNick(any()) } answers { null }
        every { userRepository.save(any()) } answers { mockedUser }
        userServiceImpl.createUser(userRequest)
        verify { userRepository.save(any()) }
        verify { userRoleRepository.findUserRoleByName("TRAVELER") }
    }

    @Test
    fun `Test creating user when user with the same nick exists`() {
        every { adminRepository.findAdminByNick(any()) } answers { null }
        every { userRepository.findByNick(any()) } answers { mockedUser }
        assertThrows<UserAlreadyExistException> { userServiceImpl.createUser(userRequest) }
    }

    @Test
    fun `Test creating user when admin with the same nick exists`() {
        every { adminRepository.findAdminByNick(any()) } answers { mockedAdmin }
        every { userRepository.findByNick(any()) } answers { null }
        assertThrows<UserAlreadyExistException> { userServiceImpl.createUser(userRequest) }
    }

    @Test
    fun `Test removing old registration requests`() {
        userServiceImpl.removeOldRegistrationRequests(LocalDate.now());
        verify { userRepository.findAllByRegistrationDateBefore(LocalDate.now().minusDays(7)) }
        verify { userRepository.deleteAll(Lists.newArrayList(mockedUser)) }
    }

    private val mockedRole = UserRole().apply {
        id = 1
        name = "TRAVELER"
    }

    private val mockedUser = User().apply {
        id = 1;
        email = "tst@ya.ru"
        name = "test-name"
        surname = "test-surname"
        nick = "nick"
        password = "12345"
        birthDate = LocalDate.now()
        registrationDate = LocalDate.now()
        userType = mockedRole
        email = "test@mail.ru"
        phone = "8-912-345-67-89"
    }

    private val mockedAdmin = Admin().apply {
        id = 1
        name = "cat"
        surname = "name"
        salary = 100
        nick = "nick"
    }

    private val userRequest = UserInfo().apply {
        email = "tst@ya.ru"
        name = "test-name"
        surname = "test-surname"
        nick = "nick"
        password = "12345"
        birthDate = "29.09.2022"
        userType = "TRAVELER"
        email = "test@mail.ru"
        phone = "8-912-345-67-89"
    }

}