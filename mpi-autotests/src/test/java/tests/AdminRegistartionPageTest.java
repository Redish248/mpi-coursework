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
import pages.AdminRegistrationPage;
import pages.LogInPage;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdminRegistartionPageTest {
    private WebDriver webDriver;

    private AdminRegistrationPage adminRegistrationPage;

    @BeforeEach
    public void setUp() {
        webDriver = WebDriverConfiguration.getWebDriver(WebDriverConfiguration.Browser.CHROME);
        LogInPage logInPage = new LogInPage(webDriver);
        adminRegistrationPage = new AdminRegistrationPage(webDriver);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        logInPage.signIn("redish", "12345");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-map")));
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        webDriver.navigate().to("http://localhost:4200/approvals");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"btn-activate\"]")));
    }

    @Test
    @DisplayName("Test that admin page for user approvals available")
    public void testAdminPage() {
        assertAll(
                () -> assertTrue(adminRegistrationPage.isUserTableVisible()),
                () -> assertTrue(adminRegistrationPage.isActivateButtonVisible()),
                () -> assertTrue(adminRegistrationPage.isDeleteButtonVisible())
        );
    }

    @AfterEach
    public void tearDown(){
        webDriver.quit();
    }
}
