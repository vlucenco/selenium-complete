package com.vlucenco.selenium.appmanager;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AdminHelper extends HelperBase {

    AdminHelper(ApplicationManager app) {
        super(app);
    }

    public void loginToAdminPanel() {
        adminPanelLoginPage.open();
        if (!isElementPresent(adminPanelLoginPage.signOutButton)) {
            adminPanelLoginPage.usernameInput().sendKeys("admin");
            adminPanelLoginPage.passwordInput().sendKeys("admin");
            adminPanelLoginPage.loginButton().click();
            wait.until(driver -> findElement(adminPanelLoginPage.signOutButton));
        }
    }

    public Set<String> getCustomerIds() {
        loginToAdminPanel();
        customerListPage.open();
        return customerListPage.getCustomerIds();
    }

    public void verifyHeadersOnAllAdminPages() {
        for (int menuIndex = 0; menuIndex < findElements(adminHomePage.menuItems).size(); menuIndex++) {
            checkHeaderPresent(menuIndex, adminHomePage.menuItems);

            for (int subMenuIndex = 0; subMenuIndex < findElements(adminHomePage.subMenuItems).size(); subMenuIndex++) {
                checkHeaderPresent(subMenuIndex, adminHomePage.subMenuItems);
            }
        }
    }

    private void checkHeaderPresent(int index, By locator) {
        List<WebElement> elements = findElements(locator);
        elements.get(index).click();
        Assert.assertTrue(findElement(By.tagName("h1")).isDisplayed());
    }

    public List<WebElement> getAllCountries() {
        app.goTo().countriesAdminPage();
        return countriesAdminPage.countries();
    }

    public List<WebElement> getCountriesWithGeoZones() {
        app.goTo().geoZonesAdminPage();
        return geoZonesAdminPage.countries();
    }

    public boolean countryHasZones(WebElement country) {
        return countriesAdminPage.getZonesNumber(country) > 0;
    }

    public void verifyCountryZonesSorting(WebElement country) {
        country.click();
        verifyZonesSorting(countriesAdminPage.zones());
    }

    public void verifyGeoZonesSorting(WebElement country) {
        country.click();
        verifyZonesSorting(geoZonesAdminPage.zones());
    }

    private void verifyZonesSorting(List<WebElement> zones) {
        List<String> actuallySortedZones = new ArrayList<>();
        zones.forEach(zone -> actuallySortedZones.add(zone.getText()));
        verifyCollectionSorted(actuallySortedZones);
    }

    public void verifyCountriesSorting(List<String> actuallySortedCountries) {
        verifyCollectionSorted(actuallySortedCountries);
    }
}