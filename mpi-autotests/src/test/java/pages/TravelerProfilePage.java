package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TravelerProfilePage extends PageObject {
    public TravelerProfilePage(WebDriver driver) {
        super(driver);
    }

    private final String USER_IMG = "//app-user-profile//img";

    private final String NAME_INPUT = "//*[@id=\"name\"]";
    private final String SURNAME_INPUT = "//*[@id=\"surname\"]";
    private final String EMAIL_INPUT = "//*[@id=\"email\"]";
    private final String PHONE_INPUT = "//*[@id=\"phone\"]";
    private final String BIRTHDATE_INPUT = "//*[@id=\"birthDate\"]";

    private final String SAVE_BUTTON = "//*[@id=\"btn-save\"]";

    @FindBy(xpath = USER_IMG)
    private WebElement userImg;

    @FindBy(xpath = NAME_INPUT)
    private WebElement nameInput;

    @FindBy(xpath = SURNAME_INPUT)
    private WebElement surnameInput;

    @FindBy(xpath = EMAIL_INPUT)
    private WebElement emailInput;

    @FindBy(xpath = PHONE_INPUT)
    private WebElement phoneInput;

    @FindBy(xpath = BIRTHDATE_INPUT)
    private WebElement birthdateInput;

    @FindBy(xpath = SAVE_BUTTON)
    private WebElement saveButton;

    public boolean isUserImgDisplayed() {
        return userImg.isDisplayed();
    }

    public void setName(String name) {
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    public void setSurname(String surname) {
        surnameInput.clear();
        surnameInput.sendKeys(surname);
    }

    public void setEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void setPhone(String phone) {
        phoneInput.clear();
        phoneInput.sendKeys(phone);
    }

    public void setBirthdate(String birthdate) {
        birthdateInput.clear();
        birthdateInput.sendKeys(birthdate);
    }

    public String getNameInputText() {
        return nameInput.getAttribute("value");
    }

    public String getSurnameInputText() {
        return surnameInput.getAttribute("value");
    }

    public String getEmailInputText() {
        return emailInput.getAttribute("value");
    }

    public String getPhoneInputText() {
        return phoneInput.getAttribute("value");
    }

    public String getBirthdateInputText() {
        return birthdateInput.getAttribute("value");
    }

    public void clickSave() {
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
        userImg.click();
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        saveButton.click();
    }

}
