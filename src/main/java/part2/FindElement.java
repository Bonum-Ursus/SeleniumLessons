package part2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class FindElement {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver",
                "src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://yandex.ru");
        driver.findElement(By.cssSelector(".input__control.input__input")).sendKeys("Погода");
        Thread.sleep(3000);
        driver.findElement(By.cssSelector(".search2__button")).click();
        Thread.sleep(3000);

    }
}
