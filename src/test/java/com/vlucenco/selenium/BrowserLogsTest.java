package com.vlucenco.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class BrowserLogsTest extends TestBase {

    @Before
    public void start() {
        driver = new ChromeDriver();
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
            List<LogEntry> logs = driver.manage().logs().get("browser").getAll();
            Assert.assertTrue(logs.size() == 0);
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
