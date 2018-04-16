package com.vlucenco.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends Page {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("http://localhost/litecart");
    }

    public WebElement loginButton() {
        return driver.findElement(By.name("login"));
    }

    public WebElement logoutButton() {
        return driver.findElement(By.linkText("Logout"));
    }

    public WebElement emailInput() {
        return driver.findElement(By.name("email"));
    }

    public WebElement passwordInput() {
        return driver.findElement(By.name("password"));
    }

    public WebElement createAccountButton() {
        return driver.findElement(By.xpath("//form[@name='login_form']//tr[5]"));
    }

    public WebElement mostPopularProduct() {
        return driver.findElement(By.cssSelector("#box-most-popular li"));
    }
}
