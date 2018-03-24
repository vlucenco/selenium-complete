package com.vlucenco.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminPanelTest extends TestBase {

    private static final String COUNTRIES_PAGE_URL = "http://localhost/litecart/admin/?app=countries&doc=countries";

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

    @Test
    public void testCountriesAndTheirZonesAreSortedAlphabetically() {
        List<WebElement> countryRows = getCountryRows();
        List<String> actuallySortedCountries = new ArrayList<>();

        for (int row = 0; row < countryRows.size(); row++) {
            if (countryHasZones(countryRows.get(row))) {
                verifyCountryZonesSorting(countryRows, row);

                countryRows = getCountryRows();
            } else {
                String countryName = countryRows.get(row).findElement(By.xpath("./td[5]")).getText();
                actuallySortedCountries.add(countryName);
            }
        }

        verifyCollectionSorted(actuallySortedCountries);
    }

    private List<WebElement> getCountryRows() {
        driver.get(COUNTRIES_PAGE_URL);
        return findElements(By.xpath("//*[@class='row']"));
    }

    private boolean countryHasZones(WebElement element) {
        try {
            int zonesNum = Integer.parseInt(element.findElement(By.xpath("./td[6]")).getText());
            return zonesNum > 0;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void verifyCountryZonesSorting(List<WebElement> countryRows, int row) {
        countryRows.get(row).findElement(By.tagName("a")).click();
        List<WebElement> zones = findElements(By.xpath("//td[3]/input[@type='hidden']/.."));
        List<String> actuallySortedZones = new ArrayList<>();
        zones.forEach(zone -> actuallySortedZones.add(zone.getText()));
        verifyCollectionSorted(actuallySortedZones);
    }

    private void verifyCollectionSorted(List<String> actuallySortedElements) {
        List<String> expectedAlphabeticallySortedElements = new ArrayList<>(actuallySortedElements)
                .stream().sorted().collect(Collectors.toList());
        Assert.assertEquals("Elements sorting not alphabetical",
                expectedAlphabeticallySortedElements, actuallySortedElements);
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}