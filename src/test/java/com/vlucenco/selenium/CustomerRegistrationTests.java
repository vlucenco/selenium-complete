package com.vlucenco.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class CustomerRegistrationTests extends TestBase {

    private Customer customer;

    @Test
    public void testRegisterCustomer() {
        customer = Customer.newEntity()
                .withFirstname("Vitali").withLastname("Lucenco").withPhone("+123456789")
                .withAddress("Address 1").withCity("New York").withPostcode("12345")
                .withCountry("United States").withZone("NY").withEmail(String.format("test+%d@test.com", System.currentTimeMillis()))
                .withPassword("drowssap").build();
        driver.get("http://localhost/litecart/en/create_account");
        submitAccountCreationForm();
        logout();
        login(customer.getEmail(), customer.getPassword());
        logout();
    }

    private void submitAccountCreationForm() {
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