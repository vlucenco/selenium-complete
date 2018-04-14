package com.vlucenco.selenium;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Set;

import static org.junit.Assert.assertTrue;

@RunWith(DataProviderRunner.class)
public class CustomerRegistrationTests extends TestBase {

    @Test
    @UseDataProvider(value = "validCustomers", location = DataProviders.class)
    public void testRegisterCustomer(Customer customer) {
        Set<String> oldIds = app.getCustomerIds();

        app.submitAccountCreationForm(customer);

        Set<String> newIds = app.getCustomerIds();

        assertTrue(newIds.containsAll(oldIds));
        assertTrue(newIds.size() == oldIds.size() + 1);

        app.customerLogout();
        app.customerLogin(customer.getEmail(), customer.getPassword());
        app.customerLogout();
    }
}