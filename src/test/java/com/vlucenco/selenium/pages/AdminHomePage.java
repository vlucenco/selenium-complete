package com.vlucenco.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdminHomePage extends Page {

    public AdminHomePage(WebDriver driver) {
        super(driver);
    }

    public By menuItems = By.id("app-");
    public By subMenuItems = By.cssSelector("#app-.selected li");

    public WebElement catalogMenuItem() {
        return driver.findElement(By.xpath("//span[contains(text(),'Catalog')]"));
    }
}
