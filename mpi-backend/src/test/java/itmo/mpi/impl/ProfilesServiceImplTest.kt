package itmo.mpi.impl

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.mockk
import itmo.mpi.entity.*
import itmo.mpi.exception.IllegalRequestParamsException
import itmo.mpi.exception.UserAlreadyHasCrewException
import itmo.mpi.exception.UserAlreadyHasShipException
import itmo.mpi.model.UserInfoUpdate
import itmo.mpi.model.profiles.RegisterCrewMemberRequest
import itmo.mpi.model.profiles.RegisterCrewRequest
import itmo.mpi.model.profiles.ShipRequest
import itmo.mpi.repository.CrewMemberRepository
import itmo.mpi.repository.CrewRepository
import itmo.mpi.repository.ShipRepository
import itmo.mpi.repository.UserRepository
import itmo.mpi.utils.CommonUtils
import org.assertj.core.util.Lists
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.core.Authentication
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.test.assertFalse
import kotlin.test.assertNull

@ExtendWith(MockKExtension::class)
class ProfilesServiceImplTest {

    @MockK
    private lateinit var userRepository: UserRepository

    @MockK
    private lateinit var crewRepository: CrewRepository

    @MockK
    private lateinit var shipRepository: ShipRepository

    @MockK
    private lateinit var crewMemberRepository: CrewMemberRepository

    @MockK
    private lateinit var commonUtils: CommonUtils

    @InjectMockKs
    private lateinit var profileServiceImpl: ProfilesServiceImpl

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Test registering new crew`() {
        every { userRepository.findByNick(any()) } returns mockedUser
        every { crewRepository.getCrewByCrewOwner(any()) } returns null
        every { crewRepository.save(any()) } returns mockedCrew
        every { crewMemberRepository.saveAll<CrewMember>(any()) } returns listOf(mockedCrewMember)

        val result = profileServiceImpl.registerCrew("nick", mockedRegisterCrewRequest)

        assertAll(
            { assertEquals(1, result.uid) },
            { assertEquals("team", result.teamName) },
            { assertEquals(5.0, result.rates) },
            { assertEquals("hash", result.photo) },
            { assertEquals("test", result.description) },
            { assertEquals(1, result.membersNumber) },
            { assertEquals(1, result.members[0].uid) },
            { assertEquals("Name", result.members[0].fullName) },
            { assertEquals(3, result.members[0].experience) }
        )
    }

    @Test
    fun `Test registering new crew with incorrect role`() {
        mockedUser.userType.name = "TRAVELER"
        every { userRepository.findByNick(any()) } returns mockedUser

        assertThrows<IllegalRequestParamsException> {
            profileServiceImpl.registerCrew(
                "nick",
                mockedRegisterCrewRequest
            )
        }
    }

    @Test
    fun `Test registering new crew when user already has crew`() {
        every { userRepository.findByNick(any()) } returns mockedUser
        every { crewRepository.getCrewByCrewOwner(any()) } returns mockedCrew

        assertThrows<UserAlreadyHasCrewException> { profileServiceImpl.registerCrew("nick", mockedRegisterCrewRequest) }
    }

    @Test
    fun `Test for updating crew`() {
        every { userRepository.findByNick(any()) } returns mockedUser
        every { crewRepository.getCrewByCrewOwner(any()) } returns mockedCrew
        every { crewRepository.save(any()) } returns mockedCrew
        justRun { crewMemberRepository.delete(any()) }
        justRun { crewMemberRepository.save(any()) }
        every { crewMemberRepository.getCrewMembersByCrewId(any()) } returns listOf(mockedCrewMember)

        val result = profileServiceImpl.updateCrew("nick", mockedRegisterCrewRequest)

        assertAll(
            { assertEquals(1, result.uid) },
            { assertEquals("team", result.teamName) },
            { assertEquals(5.0, result.rates) },
            { assertEquals("hash", result.photo) },
            { assertEquals("test", result.description) },
            { assertEquals(1, result.membersNumber) },
            { assertEquals(1, result.members[0].uid) },
            { assertEquals("Name", result.members[0].fullName) },
            { assertEquals(3, result.members[0].experience) }
        )
    }

    @Test
    fun `Test for registering ship`() {
        mockedUser.userType.name = "SHIP_OWNER"
        every { userRepository.findByNick(any()) } returns mockedUser
        every { shipRepository.getShipByOwnerId(any()) } returns null
        every { shipRepository.save(any()) } returns mockedShip

        val result = profileServiceImpl.registerShip("nick", mockedShipRequest)

        assertAll(
            { assertEquals(1, result.uid) },
            { assertEquals("name", result.title) },
            { assertEquals(10, result.speed) },
            { assertEquals(10, result.capacity) },
            { assertEquals(10, result.fuelConsumption) },
            { assertEquals(10, result.length) },
            { assertEquals(10, result.width) },
            { assertEquals(1000, result.pricePerDay) },
            { assertEquals("hash", result.photo) },
            { assertEquals("test", result.description) },
            { assertEquals(5.0, result.rates) },
        )
    }

    @Test
    fun `Test for registering ship with incorrect role`() {
        every { userRepository.findByNick(any()) } returns mockedUser

        assertThrows<IllegalRequestParamsException> { profileServiceImpl.registerShip("nick", mockedShipRequest) }
    }

    @Test
    fun `Test registering new ship when user already has ship`() {
        mockedUser.userType.name = "SHIP_OWNER"
        every { userRepository.findByNick(any()) } returns mockedUser
        every { shipRepository.getShipByOwnerId(any()) } returns mockedShip

        assertThrows<UserAlreadyHasShipException> { profileServiceImpl.registerShip("nick", mockedShipRequest) }
    }

    @Test
    fun `Test for updating ship`() {
        every { userRepository.findByNick(any()) } returns mockedUser
        every { shipRepository.getShipByOwnerId(any()) } returns mockedShip
        every { shipRepository.save(any()) } returns mockedShip

        val result = profileServiceImpl.updateShip("nick", mockedShipRequest)

        assertAll(
            { assertEquals(1, result.uid) },
            { assertEquals("name", result.title) },
            { assertEquals(10, result.speed) },
            { assertEquals(10, result.capacity) },
            { assertEquals(10, result.fuelConsumption) },
            { assertEquals(10, result.length) },
            { assertEquals(10, result.width) },
            { assertEquals(1000, result.pricePerDay) },
            { assertEquals("hash", result.photo) },
            { assertEquals("test", result.description) },
            { assertEquals(5.0, result.rates) },
        )
    }

    @Test
    fun `Test getting current user profile`() {
        every { userRepository.findByNick(any()) } returns mockedUser

        val result = profileServiceImpl.getCurrentUserProfile("nick")
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        assertAll(
            { assertEquals(1, result.uid) },
            { assertEquals("test-name", result.name) },
            { assertEquals("test-surname", result.surname) },
            { assertEquals("nick", result.nick) },
            { assertEquals(LocalDate.now().format(formatter), result.birthDate) },
            { assertEquals("test@mail.ru", result.email) },
            { assertEquals("8-912-345-67-89", result.phone) },
            { assertTrue(result.isVip) },
            { assertTrue(result.isActivated) }
        )
    }

    @Test
    fun `Test getting ships for current user`() {
        mockedUser.userType.name = "SHIP_OWNER"
        every { userRepository.findByNick(any()) } returns mockedUser
        every { shipRepository.getShipByOwnerId(any()) } returns mockedShip
        every { userRepository.findAll() } returns listOf(mockedUser)

        val result = profileServiceImpl.getShipsForCurrentUser("nick")

        assertAll(
            { assertEquals(1, result[0].uid) },
            { assertEquals("test-name", result[0].name) },
            { assertEquals("test-surname", result[0].surname) },
            { assertEquals("test@mail.ru", result[0].email) },
            { assertEquals("8-912-345-67-89", result[0].phone) },
            { assertFalse(result[0].isPirate) },
            { assertEquals(1, result[0].ship.uid) },
            { assertEquals("name", result[0].ship.title) },
            { assertEquals(10, result[0].ship.speed) },
            { assertEquals(10, result[0].ship.capacity) },
            { assertEquals(10, result[0].ship.fuelConsumption) },
            { assertEquals(10, result[0].ship.length) },
            { assertEquals(10, result[0].ship.width) },
            { assertEquals(1000, result[0].ship.pricePerDay) },
            { assertEquals("hash", result[0].ship.photo) },
            { assertEquals("test", result[0].ship.description) },
            { assertEquals(5.0, result[0].ship.rates) },
        )
    }

    @Test
    fun `Test getting ships for current user - null`() {
        every { userRepository.findByNick(any()) } returns mockedUser
        every { userRepository.findAll() } returns listOf(mockedUser)
        every { shipRepository.getShipByOwnerId(any()) } returns null

        assertEquals(0, profileServiceImpl.getShipsForCurrentUser("nick").size)
    }

    @Test
    fun `Test getting ships for current user with shareContactInfo=false`() {
        mockedUser.userType.name = "SHIP_OWNER"
        mockedUser.isShareContactInfo = false
        mockedUser.isVip = false
        every { userRepository.findByNick(any()) } returns mockedUser
        every { shipRepository.getShipByOwnerId(any()) } returns mockedShip
        every { userRepository.findAll() } returns listOf(mockedUser)

        val result = profileServiceImpl.getShipsForCurrentUser("nick")

        assertAll(
            { assertNull(result[0].email) },
            { assertNull(result[0].phone) },
            { assertNull(result[0].isPirate) }
        )
    }

    @Test
    fun `Test getting crews for current user`() {
        every { userRepository.findByNick(any()) } returns mockedUser
        every { crewRepository.getCrewByCrewOwner(any()) } returns mockedCrew
        every { userRepository.findAll() } returns listOf(mockedUser)
        every { crewMemberRepository.getCrewMembersByCrewId(any()) } answers { Lists.newArrayList(mockedCrewMember) }

        val result = profileServiceImpl.getCrewsForCurrentUser("nick")

        assertAll(
            { assertEquals(1, result[0].uid) },
            { assertEquals("test-name", result[0].name) },
            { assertEquals("test-surname", result[0].surname) },
            { assertEquals("test@mail.ru", result[0].email) },
            { assertEquals("8-912-345-67-89", result[0].phone) },
            { assertFalse(result[0].isPirate) },
            { assertEquals(1, result[0].crew.uid) },
            { assertEquals("team", result[0].crew.teamName) },
            { assertEquals(5.0, result[0].crew.rates) },
            { assertEquals("hash", result[0].crew.photo) },
            { assertEquals("test", result[0].crew.description) },
            { assertEquals(1000, result[0].crew.pricePerDay) },
            { assertEquals(1, result[0].crew.membersNumber) },
            { assertEquals(1, result[0].crew.members[0].uid) },
            { assertEquals("Name", result[0].crew.members[0].fullName) },
            { assertEquals(3, result[0].crew.members[0].experience) }
        )
    }

    @Test
    fun `Test getting crews for current user - null`() {
        every { userRepository.findByNick(any()) } returns mockedUser
        every { userRepository.findAll() } returns listOf(mockedUser)
        every { crewRepository.getCrewByCrewOwner(any()) } returns null

        assertEquals(0, profileServiceImpl.getCrewsForCurrentUser("nick").size)
    }

    @Test
    fun `Test getting crews for current user with shareContactInfo=false`() {
        mockedUser.isShareContactInfo = false
        mockedUser.isVip = false
        every { userRepository.findByNick(any()) } returns mockedUser
        every { crewRepository.getCrewByCrewOwner(any()) } returns mockedCrew
        every { userRepository.findAll() } returns listOf(mockedUser)
        every { crewMemberRepository.getCrewMembersByCrewId(any()) } returns listOf(mockedCrewMember)

        val result = profileServiceImpl.getCrewsForCurrentUser("nick")

        assertAll(
            { assertNull(result[0].email) },
            { assertNull(result[0].phone) },
            { assertNull(result[0].isPirate) }
        )
    }

    @Test
    fun `Test getting user crew`() {
        every { userRepository.findByNick(any()) } returns mockedUser
        every { crewRepository.getCrewByCrewOwner(any()) } returns mockedCrew
        every { crewMemberRepository.getCrewMembersByCrewId(any()) } returns listOf(mockedCrewMember)

        val result = profileServiceImpl.getUserCrew("nick")

        assertAll(
            { assertEquals(1, result.uid) },
            { assertEquals("team", result.teamName) },
            { assertEquals(5.0, result.rates) },
            { assertEquals("hash", result.photo) },
            { assertEquals("test", result.description) },
            { assertEquals(1, result.membersNumber) },
            { assertEquals(1, result.members[0].uid) },
            { assertEquals("Name", result.members[0].fullName) },
            { assertEquals(3, result.members[0].experience) }
        )
    }

    @Test
    fun `Test getting user crew - null`() {
        every { userRepository.findByNick(any()) } returns mockedUser
        every { crewRepository.getCrewByCrewOwner(any()) } returns null

        assertNull(profileServiceImpl.getUserCrew("nick"))
    }

    @Test
    fun `Test getting user crew for incorrect role`() {
        mockedUser.userType.name = "SHIP_OWNER"
        every { userRepository.findByNick(any()) } returns mockedUser

        assertThrows<IllegalRequestParamsException> { profileServiceImpl.getUserCrew("nick") }
    }

    @Test
    fun `Test getting user ship`() {
        mockedUser.userType.name = "SHIP_OWNER"
        every { userRepository.findByNick(any()) } returns mockedUser
        every { shipRepository.getShipByOwnerId(any()) } returns mockedShip

        val result = profileServiceImpl.getUserShip("nick")

        assertAll(
            { assertEquals(1, result.uid) },
            { assertEquals("name", result.title) },
            { assertEquals(10, result.speed) },
            { assertEquals(10, result.capacity) },
            { assertEquals(10, result.fuelConsumption) },
            { assertEquals(10, result.length) },
            { assertEquals(10, result.width) },
            { assertEquals(1000, result.pricePerDay) },
            { assertEquals("hash", result.photo) },
            { assertEquals("test", result.description) },
            { assertEquals(5.0, result.rates) },
        )
    }

    @Test
    fun `Test getting user ship - null`() {
        mockedUser.userType.name = "SHIP_OWNER"
        every { userRepository.findByNick(any()) } returns mockedUser
        every { shipRepository.getShipByOwnerId(any()) } returns null

        assertNull(profileServiceImpl.getUserShip("nick"))
    }

    @Test
    fun `Test getting user ship for incorrect role`() {
        every { userRepository.findByNick(any()) } returns mockedUser
        assertThrows<IllegalRequestParamsException> { profileServiceImpl.getUserShip("nick") }
    }

    @Test
    fun `Test updating user`() {
        every { userRepository.findByNick(any()) } returns mockedUser
        val authentication: Authentication = mockk()
        every { authentication.name } returns "nick"
        every { commonUtils.getCurrentUser() } answers { authentication }
        every { userRepository.save(any()) } answers { mockedUser }

        val result = profileServiceImpl.updateUser(mockedUserInfoUpdate)

        assertAll(
            { assertEquals("test-name", result.name) },
            { assertEquals("test-surname", result.surname) },
            { assertTrue(result.isShareContactInfo) },
            { assertEquals("8-912-345-67-89", result.phone) },
            { assertEquals("test@mail.ru", result.email) },
            { assertEquals(LocalDate.now(), result.birthDate) },
            { assertEquals("", result.password) },
        )
    }

    private val mockedShipRequest = ShipRequest().apply {
        name = "name"
        speed = 10
        capacity = 10
        fuelConsumption = 10
        length = 10
        width = 10
        pricePerDay = 1000
        photo = "hash"
        description = "test"
    }

    private val mockedShip = Ship().apply {
        id = 1
        owner = mockedUser
        name = "name"
        speed = 10
        capacity = 10
        fuelConsumption = 10
        length = 10
        width = 10
        pricePerDay = 1000
        ratesNumber = 1
        ratesAverage = 5.0
        photo = "hash"
        description = "test"
    }

    private val mockedCrew = Crew().apply {
        id = 1
        teamName = "team"
        crewOwner = mockedUser
        pricePerDay = 1000
        ratesNumber = 1
        ratesAverage = 5.0
        photo = "hash"
        description = "test"
    }

    private val mockedCrewMember = CrewMember().apply {
        id = 1
        fullName = "Name"
        experience = 3
    }

    private val mockedRegisterCrewMemberRequest = RegisterCrewMemberRequest()
        .apply {
            fullName = "Name"
            experience = 3
        }

    private val mockedRegisterCrewRequest = RegisterCrewRequest().apply {
        teamName = "team"
        photo = "hash"
        description = "test"
        pricePerDay = 1000
        members = Lists.newArrayList(mockedRegisterCrewMemberRequest)
    }

    private val mockedRole = UserRole().apply {
        id = 2
        name = "CREW_MANAGER"
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
        isShareContactInfo = true
        isVip = true
        isActivated = true
        isPirate = false
    }

    private val mockedUserInfoUpdate = UserInfoUpdate().apply {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        name = "test-name"
        surname = "test-surname"
        birthDate = LocalDate.now().format(formatter)
        isShareContacts = true
        email = "test@mail.ru"
        phone = "8-912-345-67-89"
    }
}