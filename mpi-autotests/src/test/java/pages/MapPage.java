package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MapPage extends PageObject {

    public MapPage(WebDriver driver) {
        super(driver);
    }

    private final String MAP_DIV = "/html/body/app-root/app-map/div[2]";

    @FindBy(xpath = MAP_DIV)
    WebElement mapDiv;

    public boolean isMapDivDisplayed() {
        return mapDiv.isDisplayed();
    }

}
