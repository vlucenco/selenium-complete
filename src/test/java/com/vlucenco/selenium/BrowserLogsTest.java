package com.vlucenco.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.logging.Level;

public class BrowserLogsTest extends TestBase {

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, 10);
        loginToAdminPanel();
    }

    @Test
    public void testBrowserLogs() {
        click(By.xpath("//span[contains(text(),'Catalog')]"));
        click(By.cssSelector(".fa-folder+a"));
        By productLocator = By.cssSelector(".dataTable img+a");

        List<WebElement> products = findElements(productLocator);
        for (int i = 0; i < products.size(); i++) {
            products.get(i).click();
            driver.manage().logs().get("performance").forEach(System.out::println);
            click(By.name("cancel"));
            products = findElements(productLocator);
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
