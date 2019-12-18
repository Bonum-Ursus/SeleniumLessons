package eve.part4;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Driver;

public class MyLogDriver {
    WebDriver driver;
    WebDriverWait wait;
    public BrowserMobProxy proxy;

    @Before
    public void start(){
        System.setProperty("webdriver.chrome.driver",
                "src/test/resources/chromedriver.exe");

    }
    @Test
    public void getBrowserLogs(){
        ChromeOptions opt = new ChromeOptions();
        opt.setExperimentalOption("w3c", false);

        driver = new ChromeDriver(opt);
        wait = new WebDriverWait(driver, 5);

        driver.get("http://google.ru");
        System.out.println();
        System.out.println(driver.manage().logs().getAvailableLogTypes());
        driver.manage().logs().get("browser").forEach(logEntry -> System.out.println(logEntry));
    }
    @Test
    public void testProxy() throws InterruptedException {
        proxy = new BrowserMobProxyServer();
        proxy.start(0);

        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);

        driver = new ChromeDriver(capabilities);
        wait = new WebDriverWait(driver, 5);
        proxy.newHar();
        driver.get("https://selenium2.ru/");
//        Thread.sleep(10000);
        Har har = proxy.endHar();
        har.getLog().getEntries().forEach(
                harEntry -> System.out.println(harEntry.getResponse().getStatus() +
                        " : " + harEntry.getRequest().getUrl()));
    }

    @After
    public void stop(){
        driver.close();
        driver.quit();
    }
}
