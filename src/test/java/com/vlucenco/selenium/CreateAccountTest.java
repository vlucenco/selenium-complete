package com.vlucenco.selenium;

import org.junit.Test;
import org.openqa.selenium.By;

public class CreateAccountTest extends TestBase {

    private String emailAddress;
    private String password = "password";

    @Test
    public void testCreateAccount() {
        driver.get("http://localhost/litecart");
        emailAddress = String.format("test+%d@test.com", System.currentTimeMillis());
        submitAccountCreationForm();
        logout();
        login(emailAddress, password);
        logout();
    }

    private void submitAccountCreationForm() {
        click(By.linkText("New customers click here"));
        populateField(By.name("firstname"), "Vitali");
        populateField(By.name("lastname"), "Lucenco");
        populateField(By.name("address1"), "Address 1");
        populateField(By.name("postcode"), "12345");
        populateField(By.name("city"), "New York");
        click(By.className("selection"));
        click(By.xpath("//li[contains(text(), 'United States')]"));
        click(By.cssSelector("select[name=zone_code]"));
        click(By.cssSelector("select option[value=NY]"));
        populateField(By.name("email"), emailAddress);
        populateField(By.name("phone"), "+123456789");
        populateField(By.name("password"), password);
        populateField(By.name("confirmed_password"), password);
        click(By.name("create_account"));
    }

    private void logout() {
        click(By.linkText("Logout"));
    }

    private void login(String emailAddress, String password) {
        populateField(By.name("email"), emailAddress);
        populateField(By.name("password"), password);
        click(By.name("login"));
    }
}