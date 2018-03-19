package com.vlucenco.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class AdminPanelTest extends TestBase {

    private By menuItemLocator = By.id("app-");
    private By subMenuItemLocator = By.cssSelector("#app-.selected li");


    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        loginToAdminPanel();
    }

    @Test
    public void testHeadersOnAdminPages() {
        for (int menuIndex = 0; menuIndex < findElements(menuItemLocator).size(); menuIndex++) {
            checkPageHeader(menuIndex, menuItemLocator);

            for (int subMenuIndex = 0; subMenuIndex < findElements(subMenuItemLocator).size(); subMenuIndex++) {
                checkPageHeader(subMenuIndex, subMenuItemLocator);
            }
        }
    }

    private void checkPageHeader(int index, By locator) {
        List<WebElement> elements = findElements(locator);
        elements.get(index).click();
        Assert.assertTrue(findElement(By.tagName("h1")).isDisplayed());
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}