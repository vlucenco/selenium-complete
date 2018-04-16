package com.vlucenco.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductPage extends Page {

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public By sizeOptionSelector = By.xpath("//select[@name='options[Size]']");

    public By smallSize = By.xpath("./option[2]");

    public WebElement cartQuantityElement() {
        return driver.findElement(By.cssSelector("span[class='quantity']"));
    }

    public WebElement addToCartButton() {
        return driver.findElement(By.name("add_cart_product"));
    }

    public WebElement homePageButton() {
        return driver.findElement(By.className("fa-home"));
    }

    public WebElement checkoutButton() {
        return driver.findElement(By.linkText("Checkout »"));
    }

}
