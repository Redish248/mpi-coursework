package itmo.mpi.impl

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
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
        justRun { userRepository.save(any()) }
        justRun { userRepository.delete(any()) }
    }

    @Test
    fun `Test activating user`() {
        every { userRepository.findByNick(any()) } returns User()
        every { userRepository.save(any()) } answers { nothing }

        adminServiceImpl.processUser("nick", true)

        verify { userRepository.save(any()) }
    }

    @Test
    fun `Test saving user`() {
        every { userRepository.findByNick(any()) } returns User()
        justRun { userRepository.delete(any()) }

        adminServiceImpl.processUser("nick", false)

        verify(exactly = 1) { userRepository.delete(any()) }
    }

    @Test
    fun `Test creating admin`() {
        every { userRepository.findByNick(any()) } returns null
        every { adminRepository.findAdminByNick(any()) } returns null
        every { adminRepository.save(any()) } answers { mockedAdmin }

        val admin = adminServiceImpl.createAdmin(
            "name", "surname",
            "nick", "password", 100
        )

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
        every { userRepository.findByNick(any()) } returns User()
        every { adminRepository.findAdminByNick(any()) } returns null

        assertThrows<UserAlreadyExistException> {
            adminServiceImpl.createAdmin(
                "name", "surname",
                "nick", "password", 100
            )
        }
    }

    @Test
    fun `Test creating admin when admin with the same nick exists`() {
        every { userRepository.findByNick(any()) } returns null
        every { adminRepository.findAdminByNick(any()) } returns Admin()

        assertThrows<UserAlreadyExistException> {
            adminServiceImpl.createAdmin(
                "name", "surname",
                "nick", "password", 100
            )
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