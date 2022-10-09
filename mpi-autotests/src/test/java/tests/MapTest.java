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
import pages.MapPage;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapTest {

    private WebDriver webDriver;
    private MapPage mapPage;

    @BeforeEach
    public void setUp() {
        webDriver = WebDriverConfiguration.getWebDriver(WebDriverConfiguration.Browser.CHROME);
        LogInPage logInPage = new LogInPage(webDriver);
        mapPage = new MapPage(webDriver);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        logInPage.signIn("irina", "12345");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"budgetInput\"]")));
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    @DisplayName("Test empty from island")
    public void testEmptyFromIsland() {
        mapPage.inputToIsland(2);
        mapPage.inputBudget("100");
        mapPage.inputFromDate("11.11.2022");
        mapPage.clickFindButton();
        assertEquals("Заполните все поля", mapPage.getErrorText());
    }

    @Test
    @DisplayName("Test empty from island")
    public void testEmptyToIsland() {
        mapPage.inputFromIsland(1);
        mapPage.inputBudget("100");
        mapPage.inputFromDate("11.11.2022");
        mapPage.clickFindButton();
        assertEquals("Заполните все поля", mapPage.getErrorText());
    }

    @Test
    @DisplayName("Test empty from island")
    public void testEmptyBudget() {
        mapPage.inputFromIsland(1);
        mapPage.inputToIsland(2);
        mapPage.inputFromDate("11.11.2022");
        mapPage.clickFindButton();
        assertEquals("Заполните все поля", mapPage.getErrorText());
    }

    @Test
    @DisplayName("Test empty from island")
    public void testEmptyFromDate() {
        mapPage.inputFromIsland(1);
        mapPage.inputToIsland(2);
        mapPage.inputBudget("100");
        mapPage.clickFindButton();
        assertEquals("Заполните все поля", mapPage.getErrorText());
    }

    @Test
    @DisplayName("Test correct values for not found")
    public void testCorrectValuesForNotFound() {
        mapPage.inputTripRequest(1, 2, "1", "11.11.2022");
        mapPage.clickFindButton();
        assertTrue(mapPage.isNotFoundVisible());
    }

    @Test
    @DisplayName("Test correct values")
    public void testCorrectValues() {
        mapPage.inputTripRequest(1, 2, "1000000", "11.11.2022");
        mapPage.clickFindButton();
        assertTrue(mapPage.isCreatingTripAvailable());
    }

    @Test
    @DisplayName("Test Rodos and Circle 1")
    public void testRodosCircle1() {
        mapPage.clickRodos();
        mapPage.clickCircle();
        assertAll(
                () -> assertEquals("Родос", mapPage.getInputFromIsland()),
                () -> assertEquals("Шар", mapPage.getInputToIsland())
        );
    }

    @Test
    @DisplayName("Test Rodos and Circle 2")
    public void testRodosCircle2() {
        mapPage.clickCircle();
        mapPage.clickRodos();
        assertAll(
                () -> assertEquals("Родос", mapPage.getInputToIsland()),
                () -> assertEquals("Шар", mapPage.getInputFromIsland())
        );
    }

    @Test
    @DisplayName("Test Crete and Cyprus 1")
    public void testCreteCyprus1() {
        mapPage.clickCrete();
        mapPage.clickCyprus();
        assertAll(
                () -> assertEquals("Крит", mapPage.getInputFromIsland()),
                () -> assertEquals("Кипр", mapPage.getInputToIsland())
        );
    }

    @Test
    @DisplayName("Test Crete and Cyprus 2")
    public void testCreteCyprus2() {
        mapPage.clickCyprus();
        mapPage.clickCrete();
        assertAll(
                () -> assertEquals("Кипр", mapPage.getInputFromIsland()),
                () -> assertEquals("Крит", mapPage.getInputToIsland())
        );
    }

    @Test
    @DisplayName("Test Pear and Kefalonia 1")
    public void testPearKefalonia1() {
        mapPage.clickPear();
        mapPage.clickKefalonia();
        assertAll(
                () -> assertEquals("Груша", mapPage.getInputFromIsland()),
                () -> assertEquals("Кефалония", mapPage.getInputToIsland())
        );
    }

    @Test
    @DisplayName("Test Crete and Kefalonia 2")
    public void testPearKefalonia2() {
        mapPage.clickKefalonia();
        mapPage.clickPear();
        assertAll(
                () -> assertEquals("Кефалония", mapPage.getInputFromIsland()),
                () -> assertEquals("Груша", mapPage.getInputToIsland())
        );
    }

    @Test
    @DisplayName("Test Cuba and Jamaica 1")
    public void testCubaJamaica1() {
        mapPage.clickCuba();
        mapPage.clickJamaica();
        assertAll(
                () -> assertEquals("Куба", mapPage.getInputFromIsland()),
                () -> assertEquals("Ямайка", mapPage.getInputToIsland())
        );
    }

    @Test
    @DisplayName("Test Cuba and Jamaica 2")
    public void testCubaJamaica2() {
        mapPage.clickJamaica();
        mapPage.clickCuba();
        assertAll(
                () -> assertEquals("Ямайка", mapPage.getInputFromIsland()),
                () -> assertEquals("Куба", mapPage.getInputToIsland())
        );
    }

    @AfterEach
    public void tearDown(){
        webDriver.quit();
    }

}
