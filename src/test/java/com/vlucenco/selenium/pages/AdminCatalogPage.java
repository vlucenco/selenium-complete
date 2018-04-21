package com.vlucenco.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminCatalogPage extends Page {

    public AdminCatalogPage(WebDriver driver) {
        super(driver);
    }

    public void openCategory(String category) {
        driver.findElement(By.xpath(String.format("//a[contains(text(),'%s')]", category))).click();
    }
}
