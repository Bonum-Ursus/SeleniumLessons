package eve.part3;

import com.google.common.io.Files;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

public class MyEventListener {
    public WebDriver driver;
    public WebDriverWait wait;
    public BrowserMobProxy proxy;
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
        proxy = new BrowserMobProxyServer();
        proxy.start(0);

        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);

        System.setProperty("webdriver.chrome.driver",
                "src/test/resources/chromedriver.exe");
//        driver = new EventFiringWebDriver(new ChromeDriver());
//        driver.register(new MyListener());
        driver = new ChromeDriver(capabilities);
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        wait = new WebDriverWait(driver, 3);
    }
    @Test
    public void findElementTest() throws InterruptedException {
        proxy.newHar();
        driver.get("https://yandex.ru/");
        Thread.sleep(2000);
        Har har = proxy.endHar();
        har.getLog().getEntries().forEach(
                harEntry -> System.out.println(harEntry.getResponse().getStatus() +
                        " : " + harEntry.getRequest().getUrl()));

//        System.out.println(driver.manage().logs().getAvailableLogTypes());
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
