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
import pages.TripRequestPage;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TripRequestTest {

    private WebDriver webDriver;

    private TripRequestPage tripRequestPage;

    private MapPage mapPage;

    LogInPage logInPage;

    @BeforeEach
    public void setUp() {
        webDriver = WebDriverConfiguration.getWebDriver(WebDriverConfiguration.Browser.CHROME);
        logInPage = new LogInPage(webDriver);
        tripRequestPage = new TripRequestPage(webDriver);
        mapPage = new MapPage(webDriver);
        logInPage.signIn("irina", "12345");
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-map")));
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    @DisplayName("Test full trip request creation flow with deletion")
    public void testFullRequestCreationFlowWithDeletion() {
        //создать заявку
        mapPage.inputTripRequest(1,2, "1000000", "11.11.2022");
        mapPage.clickFindButton();
        tripRequestPage.clickCreateRequest();

        //удалить
        tripRequestPage.clickDeleteRequest();

        tripRequestPage.clickEndedTab();

        assertTrue(tripRequestPage.isEndedTripDisplayed());
    }

    @Test
    @DisplayName("Test full trip request creation flow with cancellation")
    public void testFullRequestCreationFlowWithCancellation() {
        //создать заявку
        mapPage.inputTripRequest(1,2, "1000000", "11.11.2022");
        mapPage.clickFindButton();
        tripRequestPage.clickCreateRequest();

        //отменить
        tripRequestPage.clickCancelRequest();

        tripRequestPage.clickCancelledTab();

        assertTrue(tripRequestPage.isCancelledTripDisplayed());

    }

    @Test
    @DisplayName("Test full trip request creation flow")
    public void testFullRequestCreationFlow() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        //создать заявку
        mapPage.inputTripRequest(1,2, "1000000", "11.11.2022");
        mapPage.clickFindButton();
        tripRequestPage.clickCreateRequest();
        logInPage.logout();

        //одобрить владельцем экипажа
        logInPage.signIn("crew_manager", "12345");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-map")));
        webDriver.navigate().to("http://localhost:4200/requests");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"active\"]//button[1]")));
        assertTrue(tripRequestPage.isDeclineButtonClickable());
        tripRequestPage.clickAccept();
        assertTrue(tripRequestPage.isActiveTripDisplayed());
        logInPage.logout();

        //одобрить капитаном корабля
        logInPage.signIn("shipper", "12345");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-map")));
        webDriver.navigate().to("http://localhost:4200/requests");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"active\"]//button[1]")));
        assertTrue(tripRequestPage.isDeclineButtonClickable());
        tripRequestPage.clickAccept();
        assertTrue(tripRequestPage.isActiveTripDisplayed());
        logInPage.logout();

        //пользователь подтверждение
        logInPage.signIn("irina", "12345");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-map")));
        webDriver.navigate().to("http://localhost:4200/requests");
        assertTrue(tripRequestPage.isActiveTripDisplayed());
        tripRequestPage.clickAcceptRequest();

        //пользователь завершение
        tripRequestPage.clickAcceptedTab();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"end-trip-button\"]")));
        assertTrue(tripRequestPage.isAcceptedTripDisplayed());
        tripRequestPage.clickEndTrip();

        //выставление рейтинга
        tripRequestPage.clickEndedTab();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"rate-trip-btn\"]")));
        tripRequestPage.clickRateTrip();
        tripRequestPage.rateCrew("5");
        tripRequestPage.rateShip("5");
        tripRequestPage.clickRateInForm();
        assertTrue(tripRequestPage.isEndedTripDisplayed());
    }

    @AfterEach
    public void tearDown(){
        webDriver.quit();
    }
}
