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
import pages.TravelerProfilePage;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TravelerProfileTest {

    private WebDriver webDriver;

    private TravelerProfilePage travelerProfilePage;

    @BeforeEach
    public void setUp() {
        webDriver = WebDriverConfiguration.getWebDriver(WebDriverConfiguration.Browser.CHROME);
        LogInPage logInPage = new LogInPage(webDriver);
        travelerProfilePage = new TravelerProfilePage(webDriver);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        logInPage.signIn("irina", "12345");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"budgetInput\"]")));
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        webDriver.navigate().to("http://localhost:4200/profile");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"name\"]")));
    }

    @Test
    @DisplayName("Test that user profile page available")
    public void testUserProfilePageAvailability() {
        assertTrue(travelerProfilePage.isUserImgDisplayed());
    }

    @Test
    @DisplayName("Test that name can be changed")
    public void testNameInputField() {
        String previous = travelerProfilePage.getNameInputText();
        travelerProfilePage.setName("test");
        travelerProfilePage.clickSave();
        assertEquals("test", travelerProfilePage.getNameInputText());
        travelerProfilePage.setName(previous);
        travelerProfilePage.clickSave();
        assertEquals(previous, travelerProfilePage.getNameInputText());
    }

    @Test
    @DisplayName("Test that surname can be changed")
    public void testSurnameInputField() {
        String previous = travelerProfilePage.getSurnameInputText();
        travelerProfilePage.setSurname("test");
        travelerProfilePage.clickSave();
        assertEquals("test", travelerProfilePage.getSurnameInputText());
        travelerProfilePage.setSurname(previous);
        assertEquals(previous, travelerProfilePage.getSurnameInputText());
    }

    @Test
    @DisplayName("Test that email can be changed")
    public void testEmailInputField() {
        String previous = travelerProfilePage.getEmailInputText();
        travelerProfilePage.setEmail("test");
        travelerProfilePage.clickSave();
        assertEquals("test", travelerProfilePage.getEmailInputText());
        travelerProfilePage.setEmail(previous);
        assertEquals(previous, travelerProfilePage.getEmailInputText());
    }

    @Test
    @DisplayName("Test that phone can be changed")
    public void testPhoneInputField() {
        String previous = travelerProfilePage.getPhoneInputText();
        travelerProfilePage.setPhone("test");
        travelerProfilePage.clickSave();
        assertEquals("test", travelerProfilePage.getPhoneInputText());
        travelerProfilePage.setPhone(previous);
        assertEquals(previous, travelerProfilePage.getPhoneInputText());
    }

    @Test
    @DisplayName("Test that birthdate can be changed")
    public void testBirthdateInputField() {
        String previous = travelerProfilePage.getBirthdateInputText();
        travelerProfilePage.setBirthdate("test");
        travelerProfilePage.clickSave();
        assertEquals("test", travelerProfilePage.getBirthdateInputText());
        travelerProfilePage.setBirthdate(previous);
        assertEquals(previous, travelerProfilePage.getBirthdateInputText());
    }

    @AfterEach
    public void tearDown(){
        webDriver.quit();
    }
}
