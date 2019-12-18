package eve.part4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Driver;

public class MyLogDriver {
    WebDriver driver;
    WebDriverWait wait;
    @Before
    public void start(){
        System.setProperty("webdriver.chrome.driver",
                "src/test/resources/chromedriver.exe");
        ChromeOptions opt = new ChromeOptions();
        opt.setExperimentalOption("w3c", false);
        driver = new ChromeDriver(opt);
        wait = new WebDriverWait(driver, 5);
    }
    @Test
    public void getBrowserLogs(){

        driver.get("http://google.ru");
        System.out.println();
        System.out.println(driver.manage().logs().getAvailableLogTypes());
        driver.manage().logs().get("browser").forEach(logEntry -> System.out.println(logEntry));
    }

    @After
    public void stop(){
        driver.close();
        driver.quit();
    }
}
