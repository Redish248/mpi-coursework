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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogInTest {
    private WebDriver webDriver;
    private LogInPage logInPage;

    private MapPage mapPage;

    @BeforeEach
    public void setUp() {
        webDriver = WebDriverConfiguration.getWebDriver(WebDriverConfiguration.Browser.CHROME);
        logInPage = new LogInPage(webDriver);
        mapPage = new MapPage(webDriver);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"clr-form-control-1\"]")));
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        webDriver.findElement(By.xpath("//*[@id=\"clr-form-control-1\"]")).click();
    }

    @Test
    @DisplayName("Test that sign in button is disabled when only nick was added as input")
    public void testOnlyNickInput() {
        logInPage.inputNick("nick");
        assertFalse(logInPage.isSignInButtonEnabled());
    }

    @Test
    @DisplayName("Test that sign in button is disabled when only password was added as input")
    public void testOnlyPassInput() {
        logInPage.inputPassword("pass");
        assertFalse(logInPage.isSignInButtonEnabled());
    }

    @Test
    @DisplayName("Test that sign in button is enabled when both input filled")
    public void testEnablingSignInButton() {
        logInPage.inputNick("nick");
        logInPage.inputPassword("pass");
        assertTrue(logInPage.isSignInButtonEnabled());
    }

    @Test
    @DisplayName("Test correct login")
    public void testCorrectCredentials() {
        logInPage.signIn("irina", "12345");
        assertTrue(mapPage.isMapDivDisplayed());
    }

    @Test
    @DisplayName("Test incorrect nick")
    public void testInCorrectNick() {
        logInPage.signIn("irina1", "12345");
        assertTrue(logInPage.isErrorDisplayed());
    }

    @Test
    @DisplayName("Test incorrect password")
    public void testInCorrectPassword() {
        logInPage.signIn("irina", "123456");
        assertTrue(logInPage.isErrorDisplayed());
    }

    @AfterEach
    public void tearDown(){
        webDriver.quit();
    }
}
