package com.vlucenco.selenium;

import org.junit.Test;

import java.io.File;

public class AddNewProductTest extends TestBase {


    @Test
    public void testAddNewProduct() {
        app.loginToAdminPanel();
        app.productName = String.valueOf(System.currentTimeMillis());
        String basePath = new File("").getAbsolutePath();
        app.imagePath = basePath + "/src/test/resources/white-rubber-duck.jpeg";

        app.goToAddNewProductPage();
        app.populateGeneralTab();
        app.populateInformationTab();
        app.populatePricesTab();
        app.saveProduct();
        app.verifyProductWasAddedToCatalog();
    }
}