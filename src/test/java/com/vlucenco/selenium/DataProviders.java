package com.vlucenco.selenium;

import com.tngtech.java.junit.dataprovider.DataProvider;

public class DataProviders {

    @DataProvider
    public static Object[][] validCustomers() {
        return new Object[][]{
                {Customer.newEntity()
                        .withFirstname("Vitali").withLastname("Lucenco").withPhone("+123456789")
                        .withAddress("Address 1").withCity("New York").withPostcode("12345")
                        .withCountry("United States").withZone("NY")
                        .withEmail(String.format("test+%d@test.com", System.currentTimeMillis()))
                        .withPassword("drowssap").build()},
                /* ... */
        };
    }
}
