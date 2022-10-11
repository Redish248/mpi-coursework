package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverConfiguration {

    private static final String MPI_URL = "http://localhost:4200/";

    public enum Browser {
        FIREFOX, CHROME
    }

    public static WebDriver getWebDriver(Browser browser) {
        WebDriver driver =
        switch (browser) {
            case FIREFOX -> getFirefoxDriver();
            case CHROME -> getChromeDriver();
        };
        driver.manage().window().maximize();
        driver.get(MPI_URL);
        return driver;
    }

    private static FirefoxDriver getFirefoxDriver() {
        //Linux
        System.setProperty("webdriver.gecko.driver", "./drivers/linux/geckodriver");
        //Win
        //System.setProperty("webdriver.gecko.driver", "./drivers/win/geckodriver.exe");
        //MacOS
        //System.setProperty("webdriver.gecko.driver", "./drivers/mac/geckodriver");
        return new FirefoxDriver();
    }

    private static ChromeDriver getChromeDriver() {
        //Linux
        System.setProperty("webdriver.chrome.driver", "./drivers/linux/chromedriver");
        //Win
        // System.setProperty("webdriver.chrome.driver", "./drivers/win/chromedriver.exe");
        //MacOS
        // System.setProperty("webdriver.chrome.driver", "./drivers/mac/chromedriver");
        return new ChromeDriver();
    }
}
