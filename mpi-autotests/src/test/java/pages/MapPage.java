package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class MapPage extends PageObject {

    public MapPage(WebDriver driver) {
        super(driver);
    }

    private final String MAP_DIV = "/html/body/app-root/app-map/div[2]";
    private final String FROM_ISLAND = "//*[@id=\"fromIslandInput\"]";
    private final String TO_ISLAND = "//*[@id=\"toIslandInput\"]";
    private final String BUDGET = "//*[@id=\"budgetInput\"]";
    private final String FROM_DATE = "//*[@id=\"dateInput\"]";
    private final String FIND_BUTTON = "//*[@id=\"find-button\"]";
    private final String ERROR_TEXT = "//*[@id=\"errorAlert\"]/div/div";

    private final String RODOS = "//*[@id=\"rodos\"]";
    private final String CIRCLE = "//*[@id=\"circle\"]";
    private final String CYPRUS = "//*[@id=\"cyprus\"]";
    private final String CRETE = "//*[@id=\"crete\"]";
    private final String PEAR = "//*[@id=\"pear\"]";
    private final String KEFALONIA = "//*[@id=\"kefalonia\"]";
    private final String CUBA = "//*[@id=\"cuba\"]";
    private final String JAMAICA = "//*[@id=\"jamaica\"]";
    private final String NOT_FOUND = "//*[@id=\"nothing-found\"]";
    private final String CREATE_TRIP = "//*[@id=\"btn-create-request\"]";

    @FindBy(xpath = FROM_ISLAND)
    private WebElement fromIslandInput;

    @FindBy(xpath = TO_ISLAND)
    private WebElement toIslandInput;

    @FindBy(xpath = BUDGET)
    private WebElement budgetInput;

    @FindBy(xpath = FROM_DATE)
    private WebElement fromDateInput;

    @FindBy(xpath = FIND_BUTTON)
    private WebElement findButton;

    @FindBy(xpath = MAP_DIV)
    private WebElement mapDiv;

    @FindBy(xpath = ERROR_TEXT)
    private WebElement errorText;

    @FindBy(xpath = RODOS)
    private WebElement rodos;

    @FindBy(xpath = CIRCLE)
    private WebElement circle;

    @FindBy(xpath = CYPRUS)
    private WebElement cyprus;

    @FindBy(xpath = CRETE)
    private WebElement crete;

    @FindBy(xpath = PEAR)
    private WebElement pear;

    @FindBy(xpath = KEFALONIA)
    private WebElement kefalonia;

    @FindBy(xpath = CUBA)
    private WebElement cuba;

    @FindBy(xpath = JAMAICA)
    private WebElement jamaica;

    @FindBy(xpath = NOT_FOUND)
    private WebElement notFound;

    @FindBy(xpath = CREATE_TRIP)
    private WebElement createTripButton;

    public boolean isMapDivDisplayed() {
        return mapDiv.isDisplayed();
    }

    public void inputFromIsland(int fromIsland) {
        new Select(fromIslandInput).selectByIndex(fromIsland);
    }

    public void inputToIsland(int toIsland) {
        new Select(toIslandInput).selectByIndex(toIsland);
    }

    public String getInputFromIsland() {
        return new Select(fromIslandInput).getFirstSelectedOption().getText();
    }

    public String getInputToIsland() {
        return new Select(toIslandInput).getFirstSelectedOption().getText();
    }

    public void inputBudget(String budget) {
        budgetInput.sendKeys(budget);
    }

    public void inputFromDate(String fromDate) {
        fromDateInput.sendKeys(fromDate);
    }

    public void clickFindButton() {
        findButton.click();
    }

    public String getErrorText() {
        return errorText.getText();
    }

    public void inputTripRequest(int fromIsland, int toIsland, String budget, String fromDate) {
        inputFromIsland(fromIsland);
        inputToIsland(toIsland);
        inputBudget(budget);
        inputFromDate(fromDate);
    }

    public void clickRodos() {
        rodos.click();
    }

    public void clickCircle() {
        circle.click();
    }

    public void clickCyprus() {
        cyprus.click();
    }

    public void clickCrete() {
        crete.click();
    }

    public void clickPear() {
        pear.click();
    }

    public void clickKefalonia() {
        kefalonia.click();
    }

    public void clickCuba() {
        cuba.click();
    }

    public void clickJamaica() {
        jamaica.click();
    }

    public boolean isNotFoundVisible() {
        return notFound.isDisplayed();
    }

    public boolean isCreatingTripAvailable() {
        return createTripButton.isDisplayed();
    }

}
