package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CrewPage extends PageObject {
    public CrewPage(WebDriver driver) {
        super(driver);
    }

    private final String CREW_CARD = "//*[@id=\"profiles-list\"]/div[1]";
    private final String CREW_CARD_HEADER = "//*[@id=\"profiles-list\"]//[@class=\"card-header\"]";
    private final String CREW_CARD_IMG = "//*[@id=\"profiles-list\"]//img";
    private final String CREW_CARD_INFO = "//*[@class=\"card-text\"]";

    //полная карточка
    private final String FULL_CREW_CARD = "//app-view-crew-profile/clr-modal/div/div[1]/div[2]";
    private final String FULL_CREW_CARD_HEADER = "//app-view-crew-profile/clr-modal//h3";
    private final String FULL_CREW_CARD_IMG = "//app-view-crew-profile/clr-modal//img";
    private final String FULL_CREW_CARD_MEMBERS = "//app-view-crew-profile/clr-modal//table";

    //фильтры
    private final String FILTER_MEMBER_FROM = "//*[@id=\"filterMemberFrom\"]";
    private final String FILTER_MEMBER_TO = "//*[@id=\"filterMemberTo\"]";
    private final String FILTER_RATING = "//*[@id=\"filterRating\"]";

    @FindBy(xpath = CREW_CARD)
    private WebElement crewCard;

    @FindBy(xpath = CREW_CARD_HEADER)
    private WebElement crewCardHeader;

    @FindBy(xpath = CREW_CARD_IMG)
    private WebElement crewCardImg;

    @FindBy(xpath = CREW_CARD_INFO)
    private WebElement crewCardInfo;

    @FindBy(xpath = FULL_CREW_CARD)
    private WebElement fullCard;

    @FindBy(xpath = FULL_CREW_CARD_HEADER)
    private WebElement fullCardHeader;

    @FindBy(xpath = FULL_CREW_CARD_IMG)
    private WebElement fullCardImg;

    @FindBy(xpath = FULL_CREW_CARD_MEMBERS)
    private WebElement fullCardMembers;

    @FindBy(xpath = FILTER_MEMBER_FROM)
    private WebElement filterMemberFrom;

    @FindBy(xpath = FILTER_MEMBER_TO)
    private WebElement filterMemberTo;

    @FindBy(xpath = FILTER_RATING)
    private WebElement filterRating;

    public void clickCrewCard() {
        crewCard.click();
    }

    public boolean isCrewCardVisible() {
        return crewCard.isDisplayed();
    }

    public boolean isCrewCardHeaderVisible() {
        return crewCardHeader.isDisplayed();
    }

    public boolean isCrewCardImgVisible() {
        return crewCardImg.isDisplayed();
    }

    public boolean isCrewCardInfoVisible() {
        return crewCardInfo.isDisplayed();
    }

    public boolean isFullCardHeaderVisible() {
        return fullCardHeader.isDisplayed();
    }

    public boolean isFullCardVisible() {
        return fullCard.isDisplayed();
    }

    public boolean isFullCardImgVisible() {
        return fullCardImg.isDisplayed();
    }

    public boolean isFullCardMembersVisible() {
        return fullCardMembers.isDisplayed();
    }

    public void setFilterMemberFrom(String memberFrom) {
        filterMemberFrom.sendKeys(memberFrom);
    }

    public void setFilterMemberTo(String memberTo) {
        filterMemberTo.sendKeys(memberTo);
    }

    public boolean isRatingAvailable() {
        return filterRating.isDisplayed();
    }

}
