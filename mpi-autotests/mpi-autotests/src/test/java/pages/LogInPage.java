package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LogInPage extends PageObject {
    public LogInPage(WebDriver driver) {
        super(driver);
    }

    private final String NICK = "//*[@id=\"clr-form-control-5\"]";
    private final String PASSWORD = "//*[@id=\"clr-form-control-6\"]";
    private final String SIGN_IN = "/html/body/app-root/app-login/div/form/div/button[1]";

    @FindBy(xpath = NICK)
    WebElement nickInput;

    @FindBy(xpath = PASSWORD)
    WebElement passwordInput;

    @FindBy(xpath = SIGN_IN)
    WebElement signIn;

    public void inputNick(String nick) {
        nickInput.sendKeys(nick);
    }

    public void inputPassword(String pass) {
        passwordInput.sendKeys(pass);
    }

    public void clickSignIn() {
        signIn.click();
    }

    public boolean isPassInputDisplayed() {
        return passwordInput.isDisplayed();
    }

}
