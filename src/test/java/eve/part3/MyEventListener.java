package eve.part3;

import com.google.common.io.Files;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

public class MyEventListener {
    public EventFiringWebDriver driver;
    public WebDriverWait wait;
    public static class MyListener extends AbstractWebDriverEventListener{
        @Override
        public void beforeFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println(by);
        }

        @Override
        public void afterFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println(by + "found");
        }

        @Override
        public void onException(Throwable throwable, WebDriver driver) {
            System.out.println(throwable);
            File tmp = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File screen = new File("screen-" + System.currentTimeMillis() + ".png");
            try {
                Files.copy(tmp, screen);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(screen);
        }
    }

    @Before
    public void start(){
        System.setProperty("webdriver.chrome.driver",
                "src/test/resources/chromedriver.exe");
        driver = new EventFiringWebDriver(new ChromeDriver());
        driver.register(new MyListener());
        wait = new WebDriverWait(driver, 3);
    }
    @Test
    public void findElementTest() throws InterruptedException {
        driver.get("https://yandex.ru/");
        System.out.println(driver.manage().logs().getAvailableLogTypes());
//        driver.findElement(By.cssSelector("._input__control.input__input")).sendKeys("Погода");
//        driver.findElement(By.cssSelector(".search2__button")).click();
//        wait.until(ExpectedConditions.titleContains("Погода — Яндекс"));
//        Assert.assertTrue(isElementPresent(By.cssSelector(".rc")));
    }


    @After
    public void stop(){
        driver.quit();
        driver = null;
    }

    public boolean isElementPresent(By locator){
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e){
            return false;
        }
    }

}
