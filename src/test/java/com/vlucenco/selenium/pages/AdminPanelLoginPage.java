package com.vlucenco.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdminPanelLoginPage extends Page{

    public AdminPanelLoginPage(WebDriver driver) {
        super(driver);
    }

    public By signOutButton = By.className("fa-sign-out");

    public void open() {
        driver.get("http://localhost/litecart/admin/");
    }

    public WebElement usernameInput() {
        return driver.findElement(By.name("username"));
    }
    public WebElement passwordInput() {
        return driver.findElement(By.name("password"));
    }

    public WebElement loginButton() {
        return driver.findElement(By.name("login"));
    }
}
