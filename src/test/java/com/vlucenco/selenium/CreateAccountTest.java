package com.vlucenco.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateAccountTest extends TestBase {

    private String emailAddress;
    private String password = "password";

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost/litecart");
    }

    @Test
    public void testCreateAccount() {
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

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}