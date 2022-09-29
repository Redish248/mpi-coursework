package itmo.mpi.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.junit5.MockKExtension
import itmo.mpi.model.UserInfo
import itmo.mpi.service.AdminService
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

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        every { userService.findAllNotActivatedUsers() } returns mockedUsersList
        every { adminService.processUser(any(), any()) } answers { nothing }
    }

    @Test
    fun `Test getting all not activated users`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/mpi/admin/notActivatedUsers"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.`is`("name")))
    }

    @Test
    fun `Test processing user`() {
        mockMvc.perform(MockMvcRequestBuilders.post("/mpi/admin/processUser")
                .param("nick", "cat")
                .param("isActivated", "true"))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

    private val user = UserInfo().apply {
        name = "name"
    }
    private val mockedUsersList = Lists.newArrayList(user)
}