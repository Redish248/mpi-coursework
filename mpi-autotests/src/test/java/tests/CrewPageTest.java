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
import pages.CrewPage;
import pages.LogInPage;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CrewPageTest {
    private WebDriver webDriver;

    private CrewPage crewPage;

    @BeforeEach
    public void setUp() {
        webDriver = WebDriverConfiguration.getWebDriver(WebDriverConfiguration.Browser.CHROME);
        LogInPage logInPage = new LogInPage(webDriver);
        crewPage = new CrewPage(webDriver);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        logInPage.signIn("irina", "12345");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"budgetInput\"]")));
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        webDriver.navigate().to("http://localhost:4200/crews");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class=\"crew-flex\"]")));
    }

    @Test
    @DisplayName("Check that page with crew cards is available")
    public void checkShipCardVisible() {
        assertAll(
                () -> assertTrue(crewPage.isCrewCardVisible()),
                () -> assertTrue(crewPage.isCrewCardHeaderVisible()),
                () -> assertTrue(crewPage.isCrewCardInfoVisible()),
                () -> assertTrue(crewPage.isCrewCardImgVisible())
        );
    }

    @Test
    @DisplayName("Check that page with full crew cards is available")
    public void checkFullShipCardVisible() {
        crewPage.clickCrewCard();
        assertAll(
                () -> assertTrue(crewPage.isFullCardVisible()),
                () -> assertTrue(crewPage.isFullCardHeaderVisible()),
                () -> assertTrue(crewPage.isFullCardMembersVisible()),
                () -> assertTrue(crewPage.isFullCardImgVisible())
        );
    }

    @Test
    @DisplayName("Check that member from filter works")
    public void checkSpeedFilter() {
        crewPage.setFilterMemberFrom("2");
        assertTrue(crewPage.isCrewCardVisible());
    }

    @Test
    @DisplayName("Check that member to filter works")
    public void checkCapacityFilter() {
        crewPage.setFilterMemberTo("4");
        assertTrue(crewPage.isCrewCardVisible());
    }

    @Test
    @DisplayName("Check that rating filter works")
    public void checkRatingFilter() {
        assertTrue(crewPage.isRatingAvailable());
    }

    @AfterEach
    public void tearDown(){
        webDriver.quit();
    }
}
