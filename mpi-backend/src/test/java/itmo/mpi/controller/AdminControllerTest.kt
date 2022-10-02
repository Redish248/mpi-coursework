package itmo.mpi.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.*
import io.mockk.junit5.MockKExtension
import itmo.mpi.entity.Admin
import itmo.mpi.model.NewFsbAgentRequest
import itmo.mpi.model.UserInfo
import itmo.mpi.service.AdminService
import itmo.mpi.service.FsbAgentService
import itmo.mpi.service.UserService
import org.assertj.core.util.Lists
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(MockKExtension::class)
@AutoConfigureTestDatabase
@WebMvcTest(AdminController::class)
@AutoConfigureMockMvc(addFilters = false)
class AdminControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var userService: UserService

    @MockkBean
    private lateinit var adminService: AdminService

    @MockkBean
    private lateinit var fsbAgentService: FsbAgentService

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        every { userService.findAllNotActivatedUsers() } returns mockedUsersList
        every { adminService.createAdmin(any(), any(), any(), any(), any()) } returns mockedAdmin
        justRun { adminService.processUser(any(), any()) }
    }

    @Test
    fun `Test getting all not activated users`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/mpi/admin/notActivatedUsers"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.`is`("name")))
    }

    @Test
    fun `Test processing user`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/mpi/admin/processUser")
                .param("nick", "cat")
                .param("isActivated", "true")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `Check that user admin registration endpoint is available`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/mpi/admin/registerAdmin")
                .param("name", "cat")
                .param("surname", "name")
                .param("nick", "tst")
                .param("password", "1234")
                .param("salary", "100")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.`is`(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.`is`("cat")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.surname", CoreMatchers.`is`("name")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.nick", CoreMatchers.`is`("tst")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.salary", CoreMatchers.`is`(100)))
    }

    @Test
    fun `Check that fsb agent registration endpoint is available`() {
        justRun { fsbAgentService.registerNewAgent(any()) }
        mockMvc.perform(
            MockMvcRequestBuilders.post("/mpi/admin/registerFsb")
                .param("name", "test-name")
                .param("surname", "test-surname")
                .param("nick", "test-nick")
                .param("password", "12345")
                .param("salary", "500")
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)

        verify(exactly = 1) {
            fsbAgentService.registerNewAgent(
                NewFsbAgentRequest(
                    name = "test-name",
                    surname = "test-surname",
                    nick = "test-nick",
                    password = "12345"
                )
            )
        }
    }

    private val user = UserInfo().apply {
        name = "name"
    }
    private val mockedUsersList = Lists.newArrayList(user)

    private val mockedAdmin = Admin().apply {
        id = 1
        name = "cat"
        surname = "name"
        salary = 100
        nick = "tst"
    }
}