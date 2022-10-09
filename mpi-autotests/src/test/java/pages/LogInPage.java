package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LogInPage extends PageObject {
    public LogInPage(WebDriver driver) {
        super(driver);
    }

    private final String NICK = "//*[@name=\"username\"]";
    private final String PASSWORD = "//*[@name=\"password\"]";
    private final String SIGN_IN = "//*[@id=\"button-signin\"]";
    private final String SIGN_UP = "//*[@id=\"button-signup\"]";
    private final String ERROR_TEXT = "//*[@id=\"errorAlert\"]";
    private final String PROFILE_ICON = "/html/body/app-root/app-header/header/div[3]/clr-dropdown/button";
    private final String LOGOUT = "/html/body/app-root/app-header/header/div[3]/clr-dropdown/div/a[2]";

    @FindBy(xpath = NICK)
    private WebElement nickInput;

    @FindBy(xpath = PASSWORD)
    private WebElement passwordInput;

    @FindBy(xpath = SIGN_IN)
    private WebElement signIn;

    @FindBy(xpath = SIGN_UP)
    private WebElement signUp;

    @FindBy(xpath = ERROR_TEXT)
    private WebElement errorText;

    @FindBy(xpath = PROFILE_ICON)
    private WebElement profileIcon;

    @FindBy(xpath = LOGOUT)
    private WebElement logout;

    public void inputNick(String nick) {
        nickInput.sendKeys(nick);
    }

    public void inputPassword(String pass) {
        passwordInput.sendKeys(pass);
    }

    public void clickSignIn() {
        signIn.click();
    }

    public void clickSignUp() {
        signUp.click();
    }

    public boolean isignUpDisplayed() {
        return signUp.isDisplayed();
    }

    public void signIn(String nick, String pass) {
        inputNick(nick);
        inputPassword(pass);
        clickSignIn();
    }

    public boolean isSignInButtonEnabled() {
        return signIn.isEnabled();
    }

    public boolean isErrorDisplayed() {
        return errorText.isDisplayed();
    }

    public void logout() {
        profileIcon.click();
        logout.click();
    }

}
