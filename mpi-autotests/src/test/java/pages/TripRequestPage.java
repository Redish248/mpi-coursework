package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TripRequestPage extends PageObject {
    public TripRequestPage(WebDriver driver) {
        super(driver);
    }

    //menu
    private final String ACTIVE_TAB = "//*[@id=\"active-tab\"]";
    private final String CANCELLED_TAB = "//*[@id=\"cancelled-tab\"]";
    private final String ACCEPTED_TAB = "//*[@id=\"accepted-tab\"]";
    private final String ENDED_TAB = "//*[@id=\"ended-tab\"]";

    //trips
    private final String ACTIVE_TRIP = "//*[@id=\"active\"]";
    private final String ACCEPTED_TRIP = "//*[@id=\"completed\"]";
    private final String CANCELLED_TRIP = "//*[@id=\"cancelled\"]";
    private final String ENDED_TRIP = "//*[@id=\"ended\"]";

    //traveller
    private final String CREATE_REQUEST_BUTTON = "//app-options/div/div[1]/button";
    private final String ACCEPT_REQUEST_BUTTON = "//*[@id=\"active\"]//button[1]";
    private final String CANCEL_REQUEST_BUTTON = "//*[@id=\"active\"]//button[2]";
    private final String DELETE_REQUEST_BUTTON = "//*[@id=\"active\"]//button[3]";
    private final String END_TRIP_BUTTON = "//*[@id=\"end-trip-button\"]";

    private final String RATE_TRIP_BUTTON = "//*[@id=\"rate-trip-btn\"]";
    private final String RATE_TRIP_CREW = "//*[@id=\"crew-rate\"]";
    private final String RATE_TRIP_SHIP = "//*[@id=\"ship-rate\"]";
    private final String RATE_TRIP_FORM_BUTTON = "//*[@id=\"rateTrip\"]";

    //accept - decline
    private final String ACCEPT_BUTTON = "//*[@id=\"active\"]//button[1]";
    private final String DECLINE_BUTTON = "//*[@id=\"active\"]//button[2]";

    @FindBy(xpath = CREATE_REQUEST_BUTTON)
    private WebElement createRequestButton;

    @FindBy(xpath = ACCEPT_REQUEST_BUTTON)
    private WebElement acceptRequestButton;

    @FindBy(xpath = CANCEL_REQUEST_BUTTON)
    private WebElement cancelRequestButton;

    @FindBy(xpath = DELETE_REQUEST_BUTTON)
    private WebElement deleteRequestButton;

    @FindBy(xpath = END_TRIP_BUTTON)
    private WebElement endTripButton;

    @FindBy(xpath = RATE_TRIP_BUTTON)
    private WebElement rateTripButton;

    @FindBy(xpath = ACTIVE_TAB)
    private WebElement activeTab;

    @FindBy(xpath = ACCEPTED_TAB)
    private WebElement acceptedTab;

    @FindBy(xpath = CANCELLED_TAB)
    private WebElement cancelledTab;

    @FindBy(xpath = ENDED_TAB)
    private WebElement endedTab;

    @FindBy(xpath = ACTIVE_TRIP)
    private WebElement activeTrip;

    @FindBy(xpath = ACCEPTED_TRIP)
    private WebElement acceptedTrip;

    @FindBy(xpath = ENDED_TRIP)
    private WebElement endedTrip;

    @FindBy(xpath = CANCELLED_TRIP)
    private WebElement cancelledTrip;

    @FindBy(xpath = ACCEPT_BUTTON)
    private WebElement acceptButton;

    @FindBy(xpath = DECLINE_BUTTON)
    private WebElement declineButton;

    @FindBy(xpath = RATE_TRIP_CREW)
    private WebElement rateCrew;

    @FindBy(xpath = RATE_TRIP_SHIP)
    private WebElement rateShip;

    @FindBy(xpath = RATE_TRIP_FORM_BUTTON)
    private WebElement rateTripFormButton;

    public void clickCreateRequest() {
        createRequestButton.click();
    }

    public void clickAcceptRequest() {
        acceptRequestButton.click();
    }

    public void clickCancelRequest() {
        cancelRequestButton.click();
    }

    public void clickDeleteRequest() {
        deleteRequestButton.click();
    }

    public void clickEndTrip() {
        endTripButton.click();
    }

    public void clickRateTrip() {
        rateTripButton.click();
    }

    public void clickAcceptedTab() {
        acceptedTab.click();
    }

    public void clickCancelledTab() {
        cancelledTab.click();
    }

    public void clickEndedTab() {
        endedTab.click();
    }

    public boolean isActiveTripDisplayed() {
        return activeTrip.isDisplayed();
    }

    public boolean isAcceptedTripDisplayed() {
        return acceptedTrip.isDisplayed();
    }

    public boolean isEndedTripDisplayed() {
        return endedTrip.isDisplayed();
    }

    public boolean isCancelledTripDisplayed() {
        return cancelledTrip.isDisplayed();
    }

    public boolean isDeclineButtonClickable() {
        return declineButton.isEnabled();
    }

    public void clickAccept() {
        acceptButton.click();
    }

    public void rateShip(String rate) {
        rateShip.sendKeys(rate);
    }

    public void rateCrew(String rate) {
        rateCrew.sendKeys(rate);
    }

    public void clickRateInForm() {
        rateTripFormButton.click();
    }

}
