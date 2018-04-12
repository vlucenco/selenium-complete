package com.vlucenco.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminPanelTest extends TestBase {

    private static final String COUNTRIES_PAGE_URL = "http://localhost/litecart/admin/?app=countries&doc=countries";
    private static final String GEO_ZONES_PAGE_URL = "http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones";

    private By menuItemLocator = By.id("app-");
    private By subMenuItemLocator = By.cssSelector("#app-.selected li");

    @Test
    public void testHeadersOnAdminPages() {
        loginToAdminPanel();
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
        loginToAdminPanel();
        List<WebElement> countryRows = goToPageAndGetCountries(COUNTRIES_PAGE_URL, By.xpath("//*[@class='row']"));
        List<String> actuallySortedCountries = new ArrayList<>();

        for (int row = 0; row < countryRows.size(); row++) {
            if (countryHasZones(countryRows.get(row))) {
                countryRows.get(row).findElement(By.tagName("a")).click();
                getCountryZonesAndVerifySorting(By.xpath("//td[3]/input[@type='hidden']/.."));

                countryRows = goToPageAndGetCountries(COUNTRIES_PAGE_URL, By.xpath("//*[@class='row']"));
            } else {
                String countryName = countryRows.get(row).findElement(By.xpath("./td[5]")).getText();
                actuallySortedCountries.add(countryName);
            }
        }

        verifyCollectionSorted(actuallySortedCountries);
    }

    @Test
    public void testGeoZonesAreSortedAlphabetically() {
        loginToAdminPanel();
        List<WebElement> countries;
        int i = 0;
        do {
            countries = goToPageAndGetCountries(GEO_ZONES_PAGE_URL, By.xpath("//td[3]/a"));
            countries.get(i).click();
            getCountryZonesAndVerifySorting(By.xpath("//td[3]/select/option[@selected]"));
            i++;
        } while (i < countries.size());
    }

    private List<WebElement> goToPageAndGetCountries(String page, By locator) {
        driver.get(page);
        return findElements(locator);
    }

    private boolean countryHasZones(WebElement element) {
        try {
            int zonesNum = Integer.parseInt(element.findElement(By.xpath("./td[6]")).getText());
            return zonesNum > 0;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void getCountryZonesAndVerifySorting(By locator) {
        List<WebElement> zones = findElements(locator);
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
}