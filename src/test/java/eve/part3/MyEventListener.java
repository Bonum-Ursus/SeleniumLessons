package eve.part3;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
        }
    }

    @Before
    public void start(){
        System.setProperty("webdriver.chrome.driver",
                "src/test/resources/chromedriver.exe");
        driver = new EventFiringWebDriver(new ChromeDriver());
        driver.register(new MyListener());
        wait = new WebDriverWait(driver, 10);
    }
    @Test
    public void findElementTest() throws InterruptedException {
        driver.get("https://yandex.ru/");
        driver.findElement(By.cssSelector(".input__control.input__input")).sendKeys("Погода");
        driver.findElement(By.cssSelector(".search2__button")).click();
        wait.until(ExpectedConditions.titleContains("Погода — Яндекс"));
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
