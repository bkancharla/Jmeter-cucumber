import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarLog;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class webdriverTest {

    public static void main(String[] args) throws InterruptedException, IOException {

        BrowserMobProxy proxy = new BrowserMobProxyServer();
        proxy.start(0);
        //drivers/chromedriver
        System.setProperty("webdriver.chrome.driver", Paths.get("./src/test/resources/drivers/chromedriver").toString());
        // get the Selenium proxy object
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        seleniumProxy.setHttpProxy("localhost:"+ proxy.getPort());
        seleniumProxy.setSslProxy("localhost:"+ proxy.getPort());
        /* configure it as a desired capability
        ChromeOptions options = new ChromeOptions();
        options.addArguments("test-type");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
*/
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
        //capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

        // start the browser up
        WebDriver driver = new ChromeDriver(capabilities);
        driver.manage().window().maximize();

        // enable more detailed HAR capture, if desired (see CaptureType for the complete list)
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
      //  proxy.addHeader("subdatatype","test-123");
        // create a new HAR with the label "yahoo.com"
        proxy.newHar("victoriassecret.com");
        Thread.sleep(2000);
        // open yahoo.com
        driver.get("https://test.victoriassecret.com/vs/sleepwear/mix-and-match");
        Thread.sleep(2000);
        driver.navigate().refresh();
        //driver.get("http://www.google.com");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("#detail-de4e63db-9a5e-4ec6-bd79-d8d6a466b0ce")).click();
       // driver.findElement(By.cssSelector("#lst-ib")).sendKeys("test");
        //driver.findElement(By.cssSelector("#lst-ib")).sendKeys(Keys.ENTER);
       // driver.findElement(By.cssSelector("#sbtc > div.gstl_0.sbdd_a > div:nth-child(2) > div.sbdd_b > div > ul > li:nth-child(11) > div > span:nth-child(1) > span > input")).click();
        //driver.findElement(By.cssSelector("#checkoutAsGuestButton")).click();
        // get the HAR data
        Har har = proxy.getHar();
        File harFile =new File("/Users/mmurugaiah/IdeaProjects/jmeter-api-performance-poc/src/main/resources/drivers/test.har");
        har.writeTo(harFile);
        proxy.stop();
        HarLog httpLog = proxy.getHar().getLog();
        jmxcreator.generateJmeterScript(httpLog);


    }

}
