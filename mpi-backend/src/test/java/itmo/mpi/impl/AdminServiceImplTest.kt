package itmo.mpi.impl

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import itmo.mpi.entity.Admin
import itmo.mpi.entity.User
import itmo.mpi.exception.UserAlreadyExistException
import itmo.mpi.repository.AdminRepository
import itmo.mpi.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class AdminServiceImplTest {

    @MockK
    private lateinit var userRepository: UserRepository

    @MockK
    private lateinit var adminRepository: AdminRepository

    @InjectMockKs
    private lateinit var adminServiceImpl: AdminServiceImpl

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        every { userRepository.save(any()) } answers { nothing }
        every { userRepository.delete(any()) } answers { nothing }
    }

    @Test
    fun `Test activating user`() {
        every { userRepository.findByNick(any()) } answers { User() }

        adminServiceImpl.processUser("nick", true)

        verify { userRepository.save(any()) }
    }

    @Test
    fun `Test saving user`() {
        every { userRepository.findByNick(any()) } answers { User() }

        adminServiceImpl.processUser("nick", false)

        verify { userRepository.delete(any()) }
    }

    @Test
    fun `Test creating admin`() {
        every { userRepository.findByNick(any()) } answers { null }
        every { adminRepository.findAdminByNick(any()) } answers { null }
        every { adminRepository.save(any()) } answers { mockedAdmin }

        val admin = adminServiceImpl.createAdmin("name", "surname",
                "nick", "password", 100)

        assertAll(
                { assertEquals("name", admin.name) },
                { assertEquals("surname", admin.surname) },
                { assertEquals("nick", admin.nick) },
                { assertEquals("hash", admin.password) },
                { assertEquals(100, admin.salary) },
        )
    }

    @Test
    fun `Test creating admin when user with the same nick exists`() {
        every { userRepository.findByNick(any()) } answers { User() }
        every { adminRepository.findAdminByNick(any()) } answers { null }

        assertThrows<UserAlreadyExistException> {
            adminServiceImpl.createAdmin("name", "surname",
                    "nick", "password", 100)
        }
    }

    @Test
    fun `Test creating admin when admin with the same nick exists`() {
        every { userRepository.findByNick(any()) } answers { null }
        every { adminRepository.findAdminByNick(any()) } answers { Admin() }

        assertThrows<UserAlreadyExistException> {
            adminServiceImpl.createAdmin("name", "surname",
                    "nick", "password", 100)
        }
    }

    private val mockedAdmin = Admin().apply {
        id = 1
        name = "name"
        surname = "surname"
        salary = 100
        nick = "nick"
        password = "hash"
    }
}