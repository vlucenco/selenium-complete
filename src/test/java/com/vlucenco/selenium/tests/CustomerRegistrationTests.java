package com.vlucenco.selenium.tests;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import com.vlucenco.selenium.model.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Set;

import static org.junit.Assert.assertTrue;

@RunWith(DataProviderRunner.class)
public class CustomerRegistrationTests extends TestBase {

    @Test
    @UseDataProvider(value = "validCustomers", location = DataProviders.class)
    public void testRegisterCustomer(Customer customer) {
        Set<String> oldIds = app.admin().getCustomerIds();

        app.goTo().customerRegistrationPage();
        app.customer().submitAccountCreationForm(customer);

        Set<String> newIds = app.admin().getCustomerIds();

        assertTrue(newIds.containsAll(oldIds));
        assertTrue(newIds.size() == oldIds.size() + 1);

        app.customer().logout();
        app.customer().login(customer.getEmail(), customer.getPassword());
        app.customer().logout();
    }
}