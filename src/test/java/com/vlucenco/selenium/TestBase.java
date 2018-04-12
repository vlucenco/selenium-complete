package com.vlucenco.selenium;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

class TestBase {

    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void start() {
        if (tlDriver.get() != null) {
            driver = tlDriver.get();
            wait = new WebDriverWait(driver, 10);
            return;
        }

        driver = new ChromeDriver();
        tlDriver.set(driver);
        wait = new WebDriverWait(driver, 10);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            driver.quit();
            driver = null;
        }));
    }

    @After
    public void stop() {
//        driver.quit();
//        driver = null;
    }

    void loginToAdminPanel() {
        driver.get("http://localhost/litecart/admin/");
        if (!isElementPresent(By.className("fa-sign-out"))) {
            findElement(By.name("username")).sendKeys("admin");
            findElement(By.name("password")).sendKeys("admin");
            findElement(By.name("login")).click();
            wait.until(driver -> findElement(By.className("fa-sign-out")));
        }
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