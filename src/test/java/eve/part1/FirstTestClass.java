package eve.part1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class FirstTestClass {
    private WebDriver driver;
    private WebDriverWait wait;


    @Before
    public void start(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-fullscreen");
//                driver = new ChromeDriver(options);

//        System.setProperty("webdriver.chrome.driver",
//                "src/test/resources/chromedriver.exe");

//        System.setProperty("webdriver.ie.driver",
//                "src/test/resources/IEDriverServer.exe");


        System.setProperty("webdriver.gecko.driver",
                "src/test/resources/geckodriver.exe");

//        driver = new InternetExplorerDriver();
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);


        //Позволяет ожидать загрузки старницы для действия, но не более 10 секунд
        // (10с - настраиваемый через аргументы ф-ции параметр)
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void firstTest() throws InterruptedException {
        driver.get("https://www.google.ru/");
        System.out.println("****************");
        driver.findElement(By.name("q")).sendKeys("webdriver");
        Thread.sleep(2000);
        driver.findElement(By.name("btnK")).click();
        System.out.println("___________________");
        wait.until(titleIs("webdriver - Поиск в Google"));
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
