package com.vlucenco.selenium.tests;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import com.vlucenco.selenium.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class AddNewProductTest extends TestBase {

    @Test
    @UseDataProvider(value = "products", location = DataProviders.class)
    public void testAddNewProduct(Product product) {
        app.admin().loginToAdminPanel();
        app.goTo().newProductPage();
        app.product().populateGeneralTab(product);
        app.product().populateInformationTab(product);
        app.product().populatePricesTab(product);
        app.product().saveProduct();
        app.product().verifyProductWasAddedToCatalog(product);
    }
}