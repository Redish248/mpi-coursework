package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverConfiguration {

    private static final String MPI_URL = "https://localhost:4200";

    public enum Browser {
        FIREFOX, CHROME, YANDEX
    }

    public static WebDriver getWebDriver(Browser browser) {
        WebDriver driver;
        switch (browser) {
            case YANDEX:
                driver = getYandexDriver();
                break;
            case FIREFOX:
                driver = getFirefoxDriver();
                break;
            case CHROME:
            default:
                driver = getChromeDriver();
                break;
        }
        driver.manage().window().maximize();
        // driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
        driver.get(MPI_URL);
        return driver;
    }

    private static FirefoxDriver getFirefoxDriver() {
        //Linux
        System.setProperty("webdriver.chrome.driver", "./drivers/linux/chromedriver");
        //Win
        // System.setProperty("webdriver.chrome.driver", "./drivers/win/chromedriver.exe");
        //MacOS
        // System.setProperty("webdriver.chrome.driver", "./drivers/mac/chromedriver");
        return new FirefoxDriver();
    }

    private static ChromeDriver getChromeDriver() {
        //Linux
        System.setProperty("webdriver.gecko.driver", "./drivers/linux/geckodriver");
        //Win
        //System.setProperty("webdriver.gecko.driver", "./drivers/win/geckodriver.exe");
        //MacOS
        //System.setProperty("webdriver.gecko.driver", "./drivers/mac/geckodriver");
        return new ChromeDriver();
    }

    private static ChromeDriver getYandexDriver() {
        //Linux
        System.setProperty("webdriver.chrome.driver", "./drivers/linux/chromedriver");
        //Win
        //System.setProperty("webdriver.gecko.driver", "./drivers/win/yandexdriver.exe");
        //MacOS
        //System.setProperty("webdriver.gecko.driver", "./drivers/mac/yandexdriver");
        ChromeOptions options = new ChromeOptions();
        options.setBinary("./drivers/linux/yandexdriver");
        return new ChromeDriver(options);
    }
}
