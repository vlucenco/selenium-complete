package com.vlucenco.selenium;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertTrue;

@RunWith(DataProviderRunner.class)
public class CustomerRegistrationTests extends TestBase {

    @Test
    @UseDataProvider(value = "validCustomers", location = DataProviders.class)
    public void testRegisterCustomer(Customer customer) {
        Set<String> oldIds = getCustomerIds();

        submitAccountCreationForm(customer);

        Set<String> newIds = getCustomerIds();

        assertTrue(newIds.containsAll(oldIds));
        assertTrue(newIds.size() == oldIds.size() + 1);

        customerLogout();
        customerLogin(customer.getEmail(), customer.getPassword());
        customerLogout();
    }

    private Set<String> getCustomerIds() {
        loginToAdminPanel();

        driver.get("http://localhost/litecart/admin/?app=customers&doc=customers");
        return driver.findElements(By.cssSelector("table.dataTable tr.row")).stream()
                .map(e -> e.findElements(By.tagName("td")).get(2).getText())
                .collect(toSet());
    }

    private void submitAccountCreationForm(Customer customer) {
        driver.get("http://localhost/litecart/en/create_account");
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

    private void customerLogout() {
        driver.get("http://localhost/litecart");
        click(By.linkText("Logout"));
    }

    private void customerLogin(String emailAddress, String password) {
        populateField(By.name("email"), emailAddress);
        populateField(By.name("password"), password);
        click(By.name("login"));
    }
}