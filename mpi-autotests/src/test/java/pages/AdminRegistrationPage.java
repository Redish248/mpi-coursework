package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminRegistrationPage extends PageObject {
    public AdminRegistrationPage(WebDriver driver) {
        super(driver);
    }

    private final String USER_TABLE = "//app-user-approval/div/clr-datagrid";
    private final String USER_TABLE_ACTIVATE = "//*[@id=\"btn-activate\"]";
    private final String USER_TABLE_DELETE = "//*[@id=\"btn-delete\"]";

    @FindBy(xpath = USER_TABLE)
    private WebElement userTable;

    @FindBy(xpath = USER_TABLE_ACTIVATE)
    private WebElement userTableActivate;

    @FindBy(xpath = USER_TABLE_DELETE)
    private WebElement userTableDelete;

    public boolean isUserTableVisible() {
        return userTable.isDisplayed();
    }

    public boolean isActivateButtonVisible() {
        return userTableActivate.isDisplayed();
    }

    public boolean isDeleteButtonVisible() {
        return userTableDelete.isDisplayed();
    }
}
