package com.vlucenco.selenium;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

@RunWith(DataProviderRunner.class)
public class CustomerRegistrationTests extends TestBase {

    @Test
    @UseDataProvider(value = "validCustomers", location = DataProviders.class)
    public void testRegisterCustomer(Customer customer) {
        driver.get("http://localhost/litecart/en/create_account");
        submitAccountCreationForm(customer);
        logout();
        login(customer.getEmail(), customer.getPassword());
        logout();
    }

    private void submitAccountCreationForm(Customer customer) {
        populateField(By.name("firstname"), customer.getFirstname());
        populateField(By.name("lastname"), customer.getLastname());
        populateField(By.name("address1"), customer.getAddress());
        populateField(By.name("postcode"), customer.getPostcode());
        populateField(By.name("city"), customer.getCity());

        click(By.className("selection"));
        click(By.xpath(String.format("//li[contains(text(), '%s')]", customer.getCountry())));
        wait.until((WebDriver d) -> d.findElement(
                By.cssSelector(String.format("select[name=zone_code] option[value=%s]", customer.getZone()))));
        new Select(driver.findElement(By.cssSelector("select[name=zone_code]"))).selectByValue(customer.getZone());

        populateField(By.name("email"), customer.getEmail());
        populateField(By.name("phone"), customer.getPhone());
        populateField(By.name("password"), customer.getPassword());
        populateField(By.name("confirmed_password"), customer.getPassword());
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