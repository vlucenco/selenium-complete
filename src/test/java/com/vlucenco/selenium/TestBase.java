package com.vlucenco.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

class TestBase {


    WebDriver driver;
    WebDriverWait wait;

    void loginToAdminPanel() {
        driver.get("http://localhost/litecart/admin/");
        findElement(By.name("username")).sendKeys("admin");
        findElement(By.name("password")).sendKeys("admin");
        findElement(By.name("login")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.className("fa-sign-out")));
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
}
