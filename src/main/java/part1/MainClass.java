package part1;

import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class MainClass {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver",
                "src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        String[] sb = ((HasCapabilities) driver).getCapabilities().toString().
                replaceAll("Capabilities \\{", "").split(", ");
        for (String s : sb) {
            System.out.println(s);
        }
        driver.close();
    }
}
