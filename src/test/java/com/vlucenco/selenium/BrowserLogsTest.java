package com.vlucenco.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;

import java.util.List;

public class BrowserLogsTest extends TestBase {

    @Test
    public void testBrowserLogs() {
        loginToAdminPanel();
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
}
