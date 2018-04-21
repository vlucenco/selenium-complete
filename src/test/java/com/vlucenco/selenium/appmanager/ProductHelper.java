package com.vlucenco.selenium.appmanager;

import com.vlucenco.selenium.model.Product;
import com.vlucenco.selenium.pages.NewProductPage;
import org.junit.Assert;
import org.openqa.selenium.By;

public class ProductHelper extends HelperBase {

    ProductHelper(ApplicationManager app) {
        super(app);
    }

    public void populateGeneralTab(Product product) {
        newProductPage.generalTab().setStatus(NewProductPage.Status.ENABLED);
        newProductPage.generalTab().name().sendKeys(product.getName());
        newProductPage.generalTab().code().sendKeys(product.getCode());
        newProductPage.generalTab().rootCategory().click();
        newProductPage.generalTab().setCategory(product.getCategory());
        newProductPage.generalTab().setQuantity(product.getQuantity());
        newProductPage.generalTab().uploadImage(product.getImagePath());
    }

    public void populateInformationTab(Product product) {
        newProductPage.goToInformationTab();
        newProductPage.informationTab().selectManufacturer(product.getManufacturer());
        newProductPage.informationTab().typeShortDescription(product.getShortDescription());
    }

    public void populatePricesTab(Product product) {
        newProductPage.goToPricesTab();
        newProductPage.pricesTab().setPurchasePrice(product.getPurchasePrice());
        newProductPage.pricesTab().setPriceUSD(product.getPriceUSD());
    }

    public void saveProduct() {
        newProductPage.saveButton().click();
    }

    public void verifyProductWasAddedToCatalog(Product product) {
        adminHomePage.catalogMenuItem().click();
        adminCatalogPage.openCategory(product.getCategory());
        Assert.assertTrue(findElement(By.linkText(product.getName())).isDisplayed());
    }
}