package eve.part2;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FindElementTest {
    public WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void start(){
        if(driver != null)
            return;
        System.setProperty("webdriver.chrome.driver",
                "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
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

    public boolean areElementsPresent(By locator){
        return driver.findElements(locator).size() > 0;
    }
}
