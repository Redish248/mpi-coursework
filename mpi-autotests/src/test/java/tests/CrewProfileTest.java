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
import pages.CrewProfilePage;
import pages.LogInPage;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CrewProfileTest {
    private WebDriver webDriver;

    private CrewProfilePage crewProfilePage;

    @BeforeEach
    public void setUp() {
        webDriver = WebDriverConfiguration.getWebDriver(WebDriverConfiguration.Browser.CHROME);
        LogInPage logInPage = new LogInPage(webDriver);
        crewProfilePage = new CrewProfilePage(webDriver);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        logInPage.signIn("crew", "12345");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-map")));
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        webDriver.navigate().to("http://localhost:4200/profile");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"crew-member-profile\"]")));
        crewProfilePage.clickEnterProfile();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"btn-crew-update\"]")));
    }

    @Test
    @DisplayName("Test that name can be changed")
    public void testNameInputField() {
        String previous = crewProfilePage.getNameInputText();
        crewProfilePage.setName("test");
        crewProfilePage.clickUpdateButton();
        assertEquals("test", crewProfilePage.getNameInputText());
        crewProfilePage.setName(previous);
        crewProfilePage.clickUpdateButton();
        assertEquals(previous, crewProfilePage.getNameInputText());
    }

    @Test
    @DisplayName("Test that price can be changed")
    public void testPriceInputField() {
        String previous = crewProfilePage.getPriceInputText();
        crewProfilePage.setPrice("2000");
        crewProfilePage.clickUpdateButton();
        assertEquals("2000", crewProfilePage.getPriceInputText());
        crewProfilePage.setPrice(previous);
        crewProfilePage.clickUpdateButton();
        assertEquals(previous, crewProfilePage.getPriceInputText());
    }

    @Test
    @DisplayName("Test that description can be changed")
    public void testDescriptionInputField() {
        String previous = crewProfilePage.getDescriptionInputText();
        crewProfilePage.setDescription("test");
        crewProfilePage.clickUpdateButton();
        assertEquals("test", crewProfilePage.getDescriptionInputText());
        crewProfilePage.setDescription(previous);
        crewProfilePage.clickUpdateButton();
        assertEquals(previous, crewProfilePage.getDescriptionInputText());
    }


    @Test
    @DisplayName("Test that members are displayed")
    public void testMembersDisplayed() {
        assertTrue(crewProfilePage.isMembersAvailable());
    }

    @AfterEach
    public void tearDown(){
        webDriver.quit();
    }
}
