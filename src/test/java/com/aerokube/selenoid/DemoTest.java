package com.aerokube.selenoid;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.URL;
import java.util.UUID;

public class DemoTest {

    private RemoteWebDriver driver;

    @Before
    public void openDriver() throws Exception {
        final DesiredCapabilities browser = DesiredCapabilities.chrome();
//        browser.setCapability("enableVideo", true);
//        browser.setCapability("enableLog", true);
        browser.setCapability("enableVNC", true);
        browser.setCapability("screenResolution", "1920x1080x24");
        driver = new RemoteWebDriver(new URL("http://192.168.56.101:4444/wd/hub"), browser);
    }

    @Test
    public void browserTest() throws Exception {
        try {
            Thread.sleep(3000);
            driver.get("https://duckduckgo.com/");
            //WebElement input = driver.findElement(By.cssSelector("input#search_form_input_homepage"));
            //*[@id="search_form_input_homepage"]
            WebElement input = driver.findElement(By.cssSelector("//*[@id=\"search_form_input_homepage\"]"));

            input.sendKeys(Keys.chord("selenium", Keys.ENTER));
            Thread.sleep(10000);
        } finally {
            takeScreenshot(driver);
        }

    }

    static void takeScreenshot(RemoteWebDriver driver) throws Exception {
        byte[] screen = ((TakesScreenshot) new Augmenter().augment(driver)).getScreenshotAs(OutputType.BYTES);
        UUID uuid = UUID.randomUUID();
        FileUtils.writeByteArrayToFile(new File(uuid.toString() + ".png"), screen);
        //FileUtils.writeByteArrayToFile(new File(driver.getSessionId() + ".png"), screen);
    }

    @After
    public void closeDriver(){
        if (driver != null){
            driver.quit();
            driver = null;
        }
    }
}
