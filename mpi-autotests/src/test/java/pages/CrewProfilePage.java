package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CrewProfilePage extends PageObject {
    public CrewProfilePage(WebDriver driver) {
        super(driver);
    }

    private final String PROFILE_BUTTON = "//*[@id=\"crew-member-profile\"]";

    private final String NAME_INPUT = "//*[@id=\"crew-name\"]";
    private final String PRICE_INPUT = "//*[@id=\"price-crew\"]";
    private final String DESCRIPTION_INPUT = "//*[@id=\"description-crew\"]";

    private final String MEMBER_DIV = "//*[@id=\"membersDiv\"]";

    private final String UPDATE_BUTTON = "//*[@id=\"btn-crew-update\"]";

    @FindBy(xpath = PROFILE_BUTTON)
    private WebElement profileButton;

    @FindBy(xpath = NAME_INPUT)
    private WebElement nameInput;

    @FindBy(xpath = PRICE_INPUT)
    private WebElement priceInput;

    @FindBy(xpath = DESCRIPTION_INPUT)
    private WebElement descriptionInput;

    @FindBy(xpath = MEMBER_DIV)
    private WebElement membersDiv;

    @FindBy(xpath = UPDATE_BUTTON)
    private WebElement updateButton;

    public void clickEnterProfile() {
        profileButton.click();
    }

    public void setName(String name) {
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    public void setPrice(String price) {
        priceInput.clear();
        priceInput.sendKeys(price);
    }

    public void setDescription(String description) {
        descriptionInput.clear();
        descriptionInput.sendKeys(description);
    }

    public String getNameInputText() {
        return nameInput.getAttribute("value");
    }

    public String getPriceInputText() {
        return priceInput.getAttribute("value");
    }

    public String getDescriptionInputText() {
        return descriptionInput.getAttribute("value");
    }

    public boolean isMembersAvailable() {
        return membersDiv.isDisplayed();
    }

    public void clickUpdateButton() {
        updateButton.click();
    }
}
