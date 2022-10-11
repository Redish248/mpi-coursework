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
import pages.ShipProfilePage;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShipProfileTest {

    private WebDriver webDriver;

    private ShipProfilePage shipProfilePage;

    @BeforeEach
    public void setUp() {
        webDriver = WebDriverConfiguration.getWebDriver(WebDriverConfiguration.Browser.CHROME);
        LogInPage logInPage = new LogInPage(webDriver);
        shipProfilePage = new ShipProfilePage(webDriver);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        logInPage.signIn("ship", "12345");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-map")));
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        webDriver.navigate().to("http://localhost:4200/profile");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ship-profile\"]")));
        shipProfilePage.clickEnterProfile();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"btn-update\"]")));
    }

    @Test
    @DisplayName("Test that name can be changed")
    public void testNameInputField() {
        String previous = shipProfilePage.getNameInputText();
        shipProfilePage.setName("test");
        shipProfilePage.clickUpdateButton();
        assertEquals("test", shipProfilePage.getNameInputText());
        shipProfilePage.setName(previous);
        shipProfilePage.clickUpdateButton();
        assertEquals(previous, shipProfilePage.getNameInputText());
    }

    @Test
    @DisplayName("Test that price can be changed")
    public void testPriceInputField() {
        String previous = shipProfilePage.getPriceInputText();
        shipProfilePage.setPrice("600");
        shipProfilePage.clickUpdateButton();
        assertEquals("600", shipProfilePage.getPriceInputText());
        shipProfilePage.setPrice(previous);
        shipProfilePage.clickUpdateButton();
        assertEquals(previous, shipProfilePage.getPriceInputText());
    }

    @Test
    @DisplayName("Test that description can be changed")
    public void testDescriptionInputField() {
        String previous = shipProfilePage.getDescriptionInputText();
        shipProfilePage.setDescription("test");
        shipProfilePage.clickUpdateButton();
        assertEquals("test", shipProfilePage.getDescriptionInputText());
        shipProfilePage.setDescription(previous);
        shipProfilePage.clickUpdateButton();
        assertEquals(previous, shipProfilePage.getDescriptionInputText());
    }

    @Test
    @DisplayName("Test that width can be changed")
    public void testWidthInputField() {
        String previous = shipProfilePage.getWidthInputText();
        shipProfilePage.setWidth("test");
        shipProfilePage.clickUpdateButton();
        assertEquals("test", shipProfilePage.getWidthInputText());
        shipProfilePage.setWidth(previous);
        shipProfilePage.clickUpdateButton();
        assertEquals(previous, shipProfilePage.getWidthInputText());
    }

    @Test
    @DisplayName("Test that length can be changed")
    public void testLengthInputField() {
        String previous = shipProfilePage.getLengthInputText();
        shipProfilePage.setLength("test");
        shipProfilePage.clickUpdateButton();
        assertEquals("test", shipProfilePage.getLengthInputText());
        shipProfilePage.setLength(previous);
        shipProfilePage.clickUpdateButton();
        assertEquals(previous, shipProfilePage.getLengthInputText());
    }

    @Test
    @DisplayName("Test that fuel can be changed")
    public void testFuelInputField() {
        String previous = shipProfilePage.getFuelInputText();
        shipProfilePage.setFuel("150");
        shipProfilePage.clickUpdateButton();
        assertEquals("150", shipProfilePage.getFuelInputText());
        shipProfilePage.setFuel(previous);
        shipProfilePage.clickUpdateButton();
        assertEquals(previous, shipProfilePage.getFuelInputText());
    }

    @Test
    @DisplayName("Test that speed can be changed")
    public void testSpeedInputField() {
        String previous = shipProfilePage.getSpeedInputText();
        shipProfilePage.setSpeed("170");
        shipProfilePage.clickUpdateButton();
        assertEquals("170", shipProfilePage.getSpeedInputText());
        shipProfilePage.setSpeed(previous);
        shipProfilePage.clickUpdateButton();
        assertEquals(previous, shipProfilePage.getSpeedInputText());
    }

    @Test
    @DisplayName("Test that capacity can be changed")
    public void testCapacityInputField() {
        String previous = shipProfilePage.getCapacityInputText();
        shipProfilePage.setCapacity("16");
        shipProfilePage.clickUpdateButton();
        assertEquals("16", shipProfilePage.getCapacityInputText());
        shipProfilePage.setCapacity(previous);
        shipProfilePage.clickUpdateButton();
        assertEquals(previous, shipProfilePage.getCapacityInputText());
    }

    @AfterEach
    public void tearDown(){
        webDriver.quit();
    }
}
