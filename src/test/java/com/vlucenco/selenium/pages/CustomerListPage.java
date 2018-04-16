package com.vlucenco.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class CustomerListPage extends Page {

    public CustomerListPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("http://localhost/litecart/admin/?app=customers&doc=customers");
    }

    public Set<String> getCustomerIds() {
        return driver.findElements(By.cssSelector("table.dataTable tr.row")).stream()
                .map(e -> e.findElements(By.tagName("td")).get(2).getText())
                .collect(toSet());
    }
}
