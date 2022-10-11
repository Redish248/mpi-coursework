package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ShipProfilePage extends PageObject {
    public ShipProfilePage(WebDriver driver) {
        super(driver);
    }

    private final String PROFILE_BUTTON = "//*[@id=\"ship-profile\"]";

    private final String NAME_INPUT = "//*[@id=\"profile-name\"]";
    private final String PRICE_INPUT = "//*[@id=\"price\"]";
    private final String DESCRIPTION_INPUT = "//*[@id=\"description\"]";
    private final String WIDTH_INPUT = "//*[@id=\"width\"]";
    private final String LENGTH_INPUT = "//*[@id=\"length\"]";
    private final String FUEL_INPUT = "//*[@id=\"fuel\"]";
    private final String SPEED_INPUT = "//*[@id=\"speed\"]";
    private final String CAPACITY_INPUT = "//*[@id=\"capacity\"]";

    private final String UPDATE_BUTTON = "//*[@id=\"btn-update\"]";

    @FindBy(xpath = PROFILE_BUTTON)
    private WebElement profileButton;

    @FindBy(xpath = NAME_INPUT)
    private WebElement nameInput;

    @FindBy(xpath = PRICE_INPUT)
    private WebElement priceInput;

    @FindBy(xpath = DESCRIPTION_INPUT)
    private WebElement descriptionInput;

    @FindBy(xpath = WIDTH_INPUT)
    private WebElement widthInput;

    @FindBy(xpath = LENGTH_INPUT)
    private WebElement lengthInput;

    @FindBy(xpath = FUEL_INPUT)
    private WebElement fuelInput;

    @FindBy(xpath = SPEED_INPUT)
    private WebElement speedInput;

    @FindBy(xpath = CAPACITY_INPUT)
    private WebElement capacityInput;

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

    public void setWidth(String width) {
        widthInput.clear();
        widthInput.sendKeys(width);
    }

    public void setLength(String length) {
        lengthInput.clear();
        lengthInput.sendKeys(length);
    }

    public void setFuel(String fuel) {
        fuelInput.clear();
        fuelInput.sendKeys(fuel);
    }

    public void setSpeed(String speed) {
        speedInput.clear();
        speedInput.sendKeys(speed);
    }

    public void setCapacity(String capacity) {
        capacityInput.clear();
        capacityInput.sendKeys(capacity);
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

    public String getWidthInputText() {
        return widthInput.getAttribute("value");
    }

    public String getLengthInputText() {
        return lengthInput.getAttribute("value");
    }

    public String getFuelInputText() {
        return fuelInput.getAttribute("value");
    }

    public String getSpeedInputText() {
        return speedInput.getAttribute("value");
    }

    public String getCapacityInputText() {
        return capacityInput.getAttribute("value");
    }

    public void clickUpdateButton() {
        updateButton.click();
    }
}
