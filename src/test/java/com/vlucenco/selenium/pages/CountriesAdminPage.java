package com.vlucenco.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CountriesAdminPage extends Page {

    public CountriesAdminPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
    }

    public List<WebElement> countries() {
        return driver.findElements(By.xpath("//*[@class='row']/td[5]/a"));
    }

    public List<WebElement> zones() {
        return driver.findElements(By.xpath("//td[3]/input[@type='hidden']/.."));
    }

    public int getZonesNumber(WebElement country) {
        return Integer.parseInt(country.findElement(By.xpath("./../../td[6]")).getText());
    }
}