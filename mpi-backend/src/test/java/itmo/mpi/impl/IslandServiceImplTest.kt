package itmo.mpi.impl

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import itmo.mpi.entity.Admin
import itmo.mpi.entity.Island
import itmo.mpi.entity.User
import itmo.mpi.repository.AdminRepository
import itmo.mpi.repository.IslandRepository
import itmo.mpi.repository.UserRepository
import org.assertj.core.util.Lists
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder

@ExtendWith(MockKExtension::class)
class IslandServiceImplTest {

    @MockK
    private lateinit var userRepository: UserRepository

    @MockK
    private lateinit var adminRepository: AdminRepository

    @MockK
    private lateinit var islandRepository: IslandRepository

    @InjectMockKs
    private lateinit var islandServiceImpl: IslandServiceImpl

    @BeforeEach
    fun setUp() {
        val island = Island()
        island.id = 1
        island.hasPirates = true
        MockKAnnotations.init(this)
        every { islandRepository.findAll() } returns Lists.newArrayList(island)

        val authentication: Authentication = mockk()
        val securityContext: SecurityContext = mockk()
        every { securityContext.authentication } returns authentication
        every { authentication.name } returns "nick"
        SecurityContextHolder.setContext(securityContext)
    }

    @Test
    fun `Test getting all islands for vip user`() {
        every { userRepository.findByNick(any()) } answers { vipUser }
        every { adminRepository.findAdminByNick(any()) } answers { null }

        val result = islandServiceImpl.getIslands()

        assertTrue { result[0].hasPirates }
    }

    @Test
    fun `Test getting all islands for not vip user`() {
        every { userRepository.findByNick(any()) } answers { notVipUser }
        every { adminRepository.findAdminByNick(any()) } answers { null }

        val result = islandServiceImpl.getIslands()

        assertFalse { result[0].hasPirates }
    }

    @Test
    fun `Test getting all islands for admin`() {
        every { userRepository.findByNick(any()) } answers { null }
        every { adminRepository.findAdminByNick(any()) } answers { Admin() }

        val result = islandServiceImpl.getIslands()

        assertTrue { result[0].hasPirates }
    }

    @Test
    fun `Test getting all islands for empty user`() {
        every { userRepository.findByNick(any()) } answers { null }
        every { adminRepository.findAdminByNick(any()) } answers { null }

        val result = islandServiceImpl.getIslands()

        assertFalse { result[0].hasPirates }
    }

    private val vipUser = User().apply {
        isVip = true
    }

    private val notVipUser = User().apply {
        isVip = false
    }

}