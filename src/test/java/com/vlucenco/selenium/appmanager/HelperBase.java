package com.vlucenco.selenium.appmanager;

import com.vlucenco.selenium.pages.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HelperBase {

    protected WebDriver driver;
    WebDriverWait wait;
    public ApplicationManager app;

    HomePage homePage;
    ProductPage productPage;
    CartPage cartPage;
    RegistrationPage registrationPage;
    AdminPanelLoginPage adminPanelLoginPage;
    CustomerListPage customerListPage;
    CountriesAdminPage countriesAdminPage;
    AdminHomePage adminHomePage;
    GeoZonesAdminPage geoZonesAdminPage;


    HelperBase(ApplicationManager app) {
        this.app = app;
        this.driver = app.driver;
        this.wait = app.wait;

        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        registrationPage = new RegistrationPage(driver);
        adminPanelLoginPage = new AdminPanelLoginPage(driver);
        customerListPage = new CustomerListPage(driver);
        countriesAdminPage = new CountriesAdminPage(driver);
        adminHomePage = new AdminHomePage(driver);
        geoZonesAdminPage = new GeoZonesAdminPage(driver);
    }

    void click(By locator) {
        driver.findElement(locator).click();
    }

    WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    boolean isElementPresent(By locator) {
        try {
            findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    void populateField(By fieldLocator, String input) {
        findElement(fieldLocator).clear();
        findElement(fieldLocator).sendKeys(input);
    }

    String anyWindowsOtherThan(Set<String> oldWindows) {
        Set<String> handles = driver.getWindowHandles();
        handles.removeAll(oldWindows);
        return handles.size() > 0 ? handles.iterator().next() : null;
    }

    void verifyCollectionSorted(List<String> actuallySortedElements) {
        List<String> expectedAlphabeticallySortedElements = new ArrayList<>(actuallySortedElements)
                .stream().sorted().collect(Collectors.toList());
        Assert.assertEquals("Elements sorting not alphabetical",
                expectedAlphabeticallySortedElements, actuallySortedElements);
    }
}
