package com.vlucenco.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage extends Page {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public By removeButton = By.name("remove_cart_item");

    public WebElement productShortcut() {
        return driver.findElement(By.cssSelector(".shortcut"));
    }

    public Integer productsNumber() {
        return driver.findElements(By.cssSelector(".shortcuts li")).size();
    }

    public WebElement orderSummaryTable() {
        return driver.findElement(By.className("dataTable"));
    }
}
