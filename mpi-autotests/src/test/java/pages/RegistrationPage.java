package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class RegistrationPage extends PageObject {

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    private final String NAME = "//*[@id=\"name\"]";
    private final String SURNAME = "//*[@id=\"surname\"]";
    private final String NICK = "//*[@id=\"nick\"]";
    private final String PASSWORD = "//*[@id=\"password\"]";
    private final String EMAIL = "//*[@id=\"email\"]";
    private final String PHONE = "//*[@id=\"phone\"]";
    private final String BIRTH_DATE = "//*[@id=\"birthDate\"]";
    private final String USER_TYPE = "//*[@id=\"userType\"]";
    private final String ERROR_TEXT = "//*[@id=\"errorAlert\"]/div/div";
    private final String BUTTON_SIGNUP = "//*[@id=\"btn-signup\"]";

    @FindBy(xpath = NAME)
    private WebElement nameInput;

    @FindBy(xpath = SURNAME)
    private WebElement surnameInput;

    @FindBy(xpath = NICK)
    private WebElement nickInput;

    @FindBy(xpath = PASSWORD)
    private WebElement passwordInput;

    @FindBy(xpath = EMAIL)
    private WebElement emailInput;

    @FindBy(xpath = PHONE)
    private WebElement phoneInput;

    @FindBy(xpath = BIRTH_DATE)
    private WebElement birthDateInput;

    @FindBy(xpath = USER_TYPE)
    private WebElement userTypeElement;

    @FindBy(xpath = ERROR_TEXT)
    private WebElement errorText;

    @FindBy(xpath = BUTTON_SIGNUP)
    private WebElement signUpButton;

    public void inputName(String name) {
        nameInput.sendKeys(name);
    }

    public void inputSurname(String surname) {
        surnameInput.sendKeys(surname);
    }

    public void inputNick(String nick) {
        nickInput.sendKeys(nick);
    }

    public void inputPassword(String password) {
        passwordInput.sendKeys(password);
    }

    public void inputEmail(String email) {
        emailInput.sendKeys(email);
    }

    public void inputPhone(String phone) {
        phoneInput.sendKeys(phone);
    }

    public void inputBirthDate(String birthDate) {
        birthDateInput.sendKeys(birthDate);
    }

    public void inputUserType(int userType) {
        new Select(userTypeElement).selectByIndex(userType);
    }

    public void clickSignup() {
        signUpButton.click();
    }

    public boolean isSignInButtonEnabled() {
        return signUpButton.isEnabled();
    }

    public String getErrorText() {
        return errorText.getText();
    }

    public void fillFields(String name, String surname, String nick, String password, String email,
                           String phone, String birthDate, int userType) {
        inputName(name);
        inputSurname(surname);
        inputNick(nick);
        inputPassword(password);
        inputEmail(email);
        inputPhone(phone);
        inputBirthDate(birthDate);
        inputUserType(userType);
    }
}
