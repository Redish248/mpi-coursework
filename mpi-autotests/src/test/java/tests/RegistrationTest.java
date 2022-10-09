package tests;

import config.WebDriverConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LogInPage;
import pages.RegistrationPage;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationTest {

    private WebDriver webDriver;
    private RegistrationPage registrationPage;

    @BeforeEach
    public void setUp() {
        webDriver = WebDriverConfiguration.getWebDriver(WebDriverConfiguration.Browser.CHROME);
        LogInPage logInPage = new LogInPage(webDriver);
        registrationPage = new RegistrationPage(webDriver);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        logInPage.clickSignUp();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"name\"]")));
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    @DisplayName("Test that registration is available with everything filled")
    public void testAllFilled() {
        registrationPage.fillFields("test", "test", "test", "12345",
        "test@ya.ru", "123-123-11-11", "11.11.2000", 2);
        assertTrue(registrationPage.isSignInButtonEnabled());
    }

    @Test
    @DisplayName("Test displaying error when name is empty")
    public void testEmptyName() {
        registrationPage.fillFields("", "test", "test", "12345",
                "test@ya.ru", "123-123-11-11", "11.11.2000", 2);
        assertFalse(registrationPage.isSignInButtonEnabled());
    }

    @Test
    @DisplayName("Test displaying error when surname is empty")
    public void testEmptySurname() {
        registrationPage.fillFields("test", "", "test", "12345",
                "test@ya.ru", "123-123-11-11", "11.11.2000", 2);
        assertFalse(registrationPage.isSignInButtonEnabled());
    }

    @Test
    @DisplayName("Test displaying error when nick is empty")
    public void testEmptyNick() {
        registrationPage.fillFields("test", "test", "", "12345",
                "test@ya.ru", "123-123-11-11", "11.11.2000", 2);
        assertFalse(registrationPage.isSignInButtonEnabled());
    }

    @Test
    @DisplayName("Test displaying error when password is empty")
    public void testEmptyPassword() {
        registrationPage.fillFields("test", "test", "test", "",
                "test@ya.ru", "123-123-11-11", "11.11.2000", 2);
        assertFalse(registrationPage.isSignInButtonEnabled());
    }

    @Test
    @DisplayName("Test displaying error when email is empty")
    public void testEmptyEmail() {
        registrationPage.fillFields("test", "test", "test", "12345",
                "", "123-123-11-11", "11.11.2000", 2);
        assertFalse(registrationPage.isSignInButtonEnabled());
    }

    @Test
    @DisplayName("Test displaying error when phone is empty")
    public void testEmptyPhone() {
        registrationPage.fillFields("test", "test", "test", "12345",
                "test@ya.ru", "", "11.11.2000", 2);
        assertFalse(registrationPage.isSignInButtonEnabled());
    }

    @Test
    @DisplayName("Test displaying error when phone is incorrect")
    public void testIncorrectPhone() {
        registrationPage.fillFields("test", "test", "test", "12345",
                "test@ya.ru", "111", "11.11.2000", 2);
        assertFalse(registrationPage.isSignInButtonEnabled());
    }

    @Test
    @DisplayName("Test displaying error when date is empty")
    public void testEmptyDate() {
        registrationPage.fillFields("test", "test", "test", "12345",
                "test@ya.ru", "123-123-11-11", "", 2);
        assertFalse(registrationPage.isSignInButtonEnabled());
    }

    @Test
    @DisplayName("Test displaying error when date is incorrect")
    public void testIncorrectDate() {
        registrationPage.fillFields("test", "test", "test", "12345",
                "test@ya.ru", "123-123-11-11", "11", 2);
        registrationPage.clickSignup();
        assertEquals("Ошибка в формате даты", registrationPage.getErrorText());
    }

    @Test
    @DisplayName("Test displaying error when user already exists")
    public void testAlreadyExistingUser() {
        registrationPage.fillFields("test", "test", "irina", "12345",
                "test@ya.ru", "123-123-11-11", "11", 2);
        registrationPage.clickSignup();
        assertEquals("Такой пользователь уже существует в системе", registrationPage.getErrorText());
    }

    @AfterEach
    public void tearDown(){
        webDriver.quit();
    }
}
