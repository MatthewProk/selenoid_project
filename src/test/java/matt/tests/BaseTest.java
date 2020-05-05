package matt.tests;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class BaseTest {

    private static Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);

    private WebDriver driver;

    @BeforeTest
    public void preparationMethod() throws MalformedURLException {
//        System.setProperty("webdriver.chrome.driver", "C:\\chromeDriver/chromedriver.exe");
        DesiredCapabilities browser = DesiredCapabilities.chrome();
        browser.setCapability("enableVNC", true);
//        driver = new ChromeDriver();
        driver = new RemoteWebDriver(
                new URL("http://18.217.14.110:4444/wd/hub"),
                browser);
    }

    @Test
    public void browserTest() throws IOException {
        try{
            driver.get("http://duckduckgo.com/");
            WebElement input = driver.findElement(By.cssSelector("input#search_form_input_homepage"));
            input.sendKeys(Keys.chord("selenium", Keys.ENTER));
            LOGGER.info("Hey Up There!");
        }finally {

            takeScreenshot(driver);
        }
    }

    public void takeScreenshot(WebDriver driver) throws IOException {
        byte[] screen = ((TakesScreenshot) new Augmenter().augment(driver)).getScreenshotAs(OutputType.BYTES);
        UUID uuid = UUID.randomUUID();
        FileUtils.writeByteArrayToFile(new File(uuid.toString() + ".png"), screen);
    }

    @AfterClass
    public void closeDriver(){
        if(driver != null){
            driver.quit();
            driver = null;
        }
    }
}
