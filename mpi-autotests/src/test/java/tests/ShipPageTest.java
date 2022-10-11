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
import pages.ShipPage;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShipPageTest {

    private WebDriver webDriver;

    private ShipPage shipPage;

    @BeforeEach
    public void setUp() {
        webDriver = WebDriverConfiguration.getWebDriver(WebDriverConfiguration.Browser.CHROME);
        LogInPage logInPage = new LogInPage(webDriver);
        shipPage = new ShipPage(webDriver);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        logInPage.signIn("irina", "12345");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"budgetInput\"]")));
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        webDriver.navigate().to("http://localhost:4200/ships");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"profiles-list\"]")));
    }

    @Test
    @DisplayName("Check that page with ship cards is available")
    public void checkShipCardVisible() {
        assertAll(
                () -> assertTrue(shipPage.isShipCardVisible()),
                () -> assertTrue(shipPage.isCardHeaderVisible()),
                () -> assertTrue(shipPage.isCardInfoVisible()),
                () -> assertTrue(shipPage.isCardImgVisible())
        );
    }

    @Test
    @DisplayName("Check that page with full ship cards is available")
    public void checkFullShipCardVisible() {
        shipPage.clickShipCard();
        assertAll(
                () -> assertTrue(shipPage.isFullShipCardVisible()),
                () -> assertTrue(shipPage.isFullCardHeaderVisible()),
                () -> assertTrue(shipPage.isFullCardVipVisible()),
                () -> assertTrue(shipPage.isFullCardImgVisible())
        );
    }

    @Test
    @DisplayName("Check that speed filter works")
    public void checkSpeedFilter() {
        shipPage.setSpeed("151");
        assertTrue(shipPage.isShipCardVisible());
    }

    @Test
    @DisplayName("Check that capacity filter works")
    public void checkCapacityFilter() {
        for (int i = 0; i < 15; i++) {
            shipPage.setCapacity();
        }
        assertTrue(shipPage.isShipCardVisible());
    }

    @Test
    @DisplayName("Check that rating filter works")
    public void checkRatingFilter() {
        shipPage.setRating();
        assertTrue(shipPage.isShipCardVisible());
    }

    @AfterEach
    public void tearDown(){
        webDriver.quit();
    }
}
