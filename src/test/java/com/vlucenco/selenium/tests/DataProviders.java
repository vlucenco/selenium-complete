package com.vlucenco.selenium.tests;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.vlucenco.selenium.model.Customer;
import com.vlucenco.selenium.model.Product;

import java.io.File;

public class DataProviders {

    @DataProvider
    public static Object[][] validCustomers() {
        return new Object[][]{
                {Customer.newEntity()
                        .withFirstname("Vitali").withLastname("Lucenco").withPhone("+123456789")
                        .withAddress("Address 1").withCity("New York").withPostcode("12345")
                        .withCountry("United States").withZone("NY")
                        .withEmail(String.format("test+%d@test.com", System.currentTimeMillis()))
                        .withPassword("drowssap")
                        .build()},
                /* ... */
        };
    }

    @DataProvider
    public static Object[][] products() {
        return new Object[][]{
                {Product.newEntity()
                        .withName(String.valueOf(System.currentTimeMillis())).withCode("rd006").withCategory("Rubber Ducks")
                        .withQuantity("50.00")
                        .withImage(new File("").getAbsolutePath() + "/src/test/resources/white-rubber-duck.jpeg")
                        .withManufacturer("ACME Corp.")
                        .withShortDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse sollicitudin ante massa, eget ornare libero porta congue.")
                        .withPurchasePrice("100").withPriceUSD("20.000")
                        .build()},
                /* ... */
        };
    }
}
