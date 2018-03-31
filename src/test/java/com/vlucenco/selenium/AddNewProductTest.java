package com.vlucenco.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

public class AddNewProductTest extends TestBase {

    private String productName;
    private String imagePath;
    private String shortDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse sollicitudin ante massa, eget ornare libero porta congue.";

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        loginToAdminPanel();
    }

    @Test
    public void testAddNewProduct() {
        productName = String.valueOf(System.currentTimeMillis());
        String basePath = new File("").getAbsolutePath();
        imagePath = basePath + "/src/test/resources/white-rubber-duck.jpeg";

        goToAddNewProductPage();
        populateGeneralTab();
        populateInformationTab();
        populatePricesTab();
        saveProduct();
        verifyProductWasAddedToCatalog();
    }

    private void goToAddNewProductPage() {
        click(By.xpath("//span[contains(text(),'Catalog')]"));
        click(By.linkText("Add New Product"));
    }

    private void populateGeneralTab() {
        click(By.xpath("//label[contains(text(),'Enabled')]"));
        populateField(By.name("name[en]"), productName);
        populateField(By.name("code"), "wd001");
        findElement(By.cssSelector("[data-name='Root']")).click();
        findElement(By.cssSelector("[data-name='Rubber Ducks']")).click();
        populateField(By.name("quantity"), "50.00");
        populateField(By.name("new_images[]"), imagePath);
    }

    private void populateInformationTab() {
        click(By.xpath("//a[contains(text(), 'Information')]"));
        click(By.name("manufacturer_id"));
        click(By.cssSelector("select[name=manufacturer_id] option[value='1']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]",
                findElement(By.cssSelector("[name='short_description[en]']")), shortDescription);
    }

    private void populatePricesTab() {
        click(By.xpath("//a[contains(text(), 'Prices')]"));
        populateField(By.name("purchase_price"), "100");
        populateField(By.name("prices[USD]"), "20.000");
    }

    private void saveProduct() {
        click(By.name("save"));
    }

    private void verifyProductWasAddedToCatalog() {
        click(By.xpath("//span[contains(text(),'Catalog')]"));
        click(By.xpath("//a[contains(text(),'Rubber Ducks')]"));
        Assert.assertTrue(findElement(By.linkText(productName)).isDisplayed());
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}