package com.vlucenco.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class GeoZonesAdminPage extends Page {

    public GeoZonesAdminPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
    }

    public List<WebElement> countries() {
        return driver.findElements(By.xpath("//td[3]/a"));
    }

    public List<WebElement> zones() {
        return driver.findElements(By.xpath("//td[3]/select/option[@selected]"));
    }

}