package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ShipPage extends PageObject {
    public ShipPage(WebDriver driver) {
        super(driver);
    }

    private final String SHIP_CARD = "//*[@id=\"profiles-list\"]/div[1]";
    private final String CARD_HEADER = "//*[@id=\"profiles-list\"]/div[1]/div[1]";
    private final String CARD_IMG = "//*[@id=\"profiles-list\"]/div[1]/div[2]/div[1]/img";
    private final String CARD_INFO = "//*[@id=\"profiles-list\"]/div[1]/div[2]/div[2]";

    //открытая карточка
    private final String FULL_CARD = "//app-view-ship-profile/clr-modal/div/div[1]/div[2]";
    private final String FULL_CARD_HEADER = "//app-view-ship-profile/clr-modal//h3";
    private final String FULL_CARD_IMG = "//app-view-ship-profile/clr-modal/div/div[1]/div[2]/div/div[2]/div/img";
    private final String FULL_CARD_VIP = "//app-view-ship-profile/clr-modal//app-pirate-label";

    //фильтры
    private final String FILTER_SPEED = "//*[@id=\"filterSpeed\"]";
    private final String FILTER_CAPACITY = "//*[@id=\"filterCapacity\"]";
    private final String FILTER_RATING = "//*[@id=\"filterRating\"]";

    @FindBy(xpath = SHIP_CARD)
    private WebElement shipCard;

    @FindBy(xpath = CARD_HEADER)
    private WebElement cardHeader;

    @FindBy(xpath = CARD_IMG)
    private WebElement cardImg;

    @FindBy(xpath = CARD_INFO)
    private WebElement cardInfo;

    @FindBy(xpath = FULL_CARD)
    private WebElement fullShipCard;

    @FindBy(xpath = FULL_CARD_HEADER)
    private WebElement fullCardHeader;

    @FindBy(xpath = FULL_CARD_IMG)
    private WebElement fullCardImg;

    @FindBy(xpath = FULL_CARD_VIP)
    private WebElement fullCardVip;

    @FindBy(xpath = FILTER_SPEED)
    private WebElement filterSpeed;

    @FindBy(xpath = FILTER_CAPACITY)
    private WebElement filterCapacity;

    @FindBy(xpath = FILTER_RATING)
    private WebElement filterRating;

    public void clickShipCard() {
        shipCard.click();
    }

    public boolean isShipCardVisible() {
        return shipCard.isDisplayed();
    }

    public boolean isCardHeaderVisible() {
        return cardHeader.isDisplayed();
    }

    public boolean isCardImgVisible() {
        return cardImg.isDisplayed();
    }

    public boolean isCardInfoVisible() {
        return cardInfo.isDisplayed();
    }

    public boolean isFullShipCardVisible() {
        return fullShipCard.isDisplayed();
    }

    public boolean isFullCardHeaderVisible() {
        return fullCardHeader.isDisplayed();
    }

    public boolean isFullCardImgVisible() {
        return fullCardImg.isDisplayed();
    }

    public boolean isFullCardVipVisible() {
        return fullCardVip.isDisplayed();
    }

    public void setSpeed(String speed) {
        filterSpeed.sendKeys(speed);
    }

    public void setCapacity() {
        filterCapacity.sendKeys(Keys.ARROW_RIGHT);
    }

    public void setRating() {
        filterRating.sendKeys(Keys.ARROW_RIGHT);
        filterRating.sendKeys(Keys.ARROW_LEFT);
    }

}
