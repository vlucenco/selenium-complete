package com.vlucenco.selenium.tests;

import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class AdminPanelTest extends TestBase {

    @Test
    public void testHeadersOnAdminPages() {
        app.admin().loginToAdminPanel();
        app.admin().verifyHeadersOnAllAdminPages();
    }

    @Test
    public void testCountriesAndTheirZonesAreSortedAlphabetically() {
        app.admin().loginToAdminPanel();
        List<WebElement> countryRows = app.admin().getAllCountries();
        List<String> actuallySortedCountries = new ArrayList<>();

        for (int row = 0; row < countryRows.size(); row++) {
            WebElement country = countryRows.get(row);
            if (app.admin().countryHasZones(country)) {
                app.admin().verifyCountryZonesSorting(country);

                countryRows = app.admin().getAllCountries();
            } else {
                actuallySortedCountries.add(country.getText());
            }
        }

        app.admin().verifyCountriesSorting(actuallySortedCountries);
    }

    @Test
    public void testGeoZonesAreSortedAlphabetically() {
        app.admin().loginToAdminPanel();
        List<WebElement> countries;
        int i = 0;
        do {
            countries = app.admin().getCountriesWithGeoZones();
            app.admin().verifyGeoZonesSorting(countries.get(i));
            i++;
        } while (i < countries.size());
    }
}