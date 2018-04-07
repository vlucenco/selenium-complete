package com.vlucenco.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

class TestBase {


    WebDriver driver;
    WebDriverWait wait;

    void loginToAdminPanel() {
        driver.get("http://localhost/litecart/admin/");
        findElement(By.name("username")).sendKeys("admin");
        findElement(By.name("password")).sendKeys("admin");
        findElement(By.name("login")).click();
        wait.until(driver -> findElement(By.className("fa-sign-out")));
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
}
