package itmo.mpi.registration

import itmo.mpi.service.UserService
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RegistrationTest {
    @Autowired
    private lateinit var userService: UserService

    @Test
    @Disabled("just for test")
    fun createUser() {
        userService.createUser(
            "test-name",
            "test-surname",
            "nick",
            "12345",
            "01.09.2017",
            "TRAVELER",
            "test@mail.ru",
            "8-912-345-67-89"
        )
    }
}