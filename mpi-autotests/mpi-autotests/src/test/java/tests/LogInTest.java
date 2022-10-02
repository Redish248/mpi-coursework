package tests;

import config.WebDriverConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LogInPage;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LogInTest {
    private WebDriver webDriver;
    private LogInPage logInPage;

    @BeforeEach
    public void setUp() {
        webDriver = WebDriverConfiguration.getWebDriver(WebDriverConfiguration.Browser.CHROME);
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        logInPage = new LogInPage(webDriver);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
     //   wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='onetrust-accept-btn-handler']")));
       // webDriver.findElement(By.xpath("//*[@id='onetrust-accept-btn-handler']")).click();
        webDriver.navigate().to("https://localhost:4200/login");
    }

    @Test
    @Order(1)
    public void testCorrectEmail() {
        logInPage.inputNick("nick");
        assertTrue(logInPage.isPassInputDisplayed());
    }
}
