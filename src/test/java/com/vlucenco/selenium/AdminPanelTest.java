package com.vlucenco.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Test
    public void testCountriesAlphabeticalSorting() {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        List<WebElement> elements = findElements(By.xpath("//*[@class='row']/td[5]"));

        List<String> actuallySortedCountries = new ArrayList<>();
        for (WebElement element : elements) {
            actuallySortedCountries.add(element.getText());
        }

        checkCollectionSorted(actuallySortedCountries);
    }

    private void checkCollectionSorted(List<String> actuallySortedCountries) {
        List<String> expectedAlphabeticallySortedCountries = new ArrayList<>(actuallySortedCountries)
                .stream().sorted().collect(Collectors.toList());
        Assert.assertEquals("Countries sorting not alphabetical", expectedAlphabeticallySortedCountries, actuallySortedCountries);
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