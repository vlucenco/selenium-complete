package com.vlucenco.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class AdminPanelTest extends TestBase {

    @Test
    public void testHeadersOnAdminPages() {
        app.loginToAdminPanel();
        app.verifyHeadersOnAllAdminPages();
    }

    @Test
    public void testCountriesAndTheirZonesAreSortedAlphabetically() {
        app.loginToAdminPanel();
        List<WebElement> countryRows = app.goToPageAndGetCountries(app.COUNTRIES_PAGE_URL, By.xpath("//*[@class='row']"));
        List<String> actuallySortedCountries = new ArrayList<>();

        for (int row = 0; row < countryRows.size(); row++) {
            if (app.countryHasZones(countryRows.get(row))) {
                countryRows.get(row).findElement(By.tagName("a")).click();
                app.getCountryZonesAndVerifySorting(By.xpath("//td[3]/input[@type='hidden']/.."));

                countryRows = app.goToPageAndGetCountries(app.COUNTRIES_PAGE_URL, By.xpath("//*[@class='row']"));
            } else {
                String countryName = countryRows.get(row).findElement(By.xpath("./td[5]")).getText();
                actuallySortedCountries.add(countryName);
            }
        }

        app.verifyCollectionSorted(actuallySortedCountries);
    }

    @Test
    public void testGeoZonesAreSortedAlphabetically() {
        app.loginToAdminPanel();
        List<WebElement> countries;
        int i = 0;
        do {
            countries = app.goToPageAndGetCountries(app.GEO_ZONES_PAGE_URL, By.xpath("//td[3]/a"));
            countries.get(i).click();
            app.getCountryZonesAndVerifySorting(By.xpath("//td[3]/select/option[@selected]"));
            i++;
        } while (i < countries.size());
    }
}