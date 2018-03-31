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
    public void testCreateAccountAndLogin() {
        emailAddress = String.format("test+%d@test.com", System.currentTimeMillis());
        submitAccountCreationForm();
        logout();
        login(emailAddress, password);
        logout();
    }

    private void submitAccountCreationForm() {
        findElement(By.linkText("New customers click here")).click();
        findElement(By.name("firstname")).sendKeys("Vitali");
        findElement(By.name("lastname")).sendKeys("Lucenco");
        findElement(By.name("address1")).sendKeys("Address 1");
        findElement(By.name("postcode")).sendKeys("12345");
        findElement(By.name("city")).sendKeys("New York");
        findElement(By.className("selection")).click();
        findElement(By.xpath("//li[contains(text(), 'United States')]")).click();
        findElement(By.cssSelector("select[name=zone_code]")).click();
        findElement(By.cssSelector("select option[value=NY]")).click();
        findElement(By.name("email")).sendKeys(emailAddress);
        findElement(By.name("phone")).sendKeys("+123456789");
        findElement(By.name("password")).sendKeys(password);
        findElement(By.name("confirmed_password")).sendKeys(password);
        findElement(By.name("create_account")).click();
    }

    private void logout() {
        findElement(By.linkText("Logout")).click();
    }

    private void login(String emailAddress, String password) {
        findElement(By.name("email")).sendKeys(emailAddress);
        findElement(By.name("password")).sendKeys(password);
        findElement(By.name("login")).click();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}