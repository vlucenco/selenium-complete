package com.vlucenco.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NewProductPage extends Page {

    private GeneralTab generalTab;
    private InformationTab informationTab;
    private PricesTab pricesTab;

    public enum Status {ENABLED, DISABLED}

    public NewProductPage(WebDriver driver) {
        super(driver);
        generalTab = new GeneralTab();
        informationTab = new InformationTab();
        pricesTab = new PricesTab();
    }

    public GeneralTab generalTab() {
        return generalTab;
    }

    public InformationTab informationTab() {
        return informationTab;
    }

    public PricesTab pricesTab() {
        return pricesTab;
    }

    public void goToInformationTab() {
        driver.findElement(By.xpath("//a[contains(text(), 'Information')]")).click();
    }

    public void goToPricesTab() {
        driver.findElement(By.xpath("//a[contains(text(), 'Prices')]")).click();
    }

    public void open() {
        driver.get("http://localhost/litecart/admin/?category_id=0&app=catalog&doc=edit_product");
    }

    public WebElement saveButton() {
        return driver.findElement(By.name("save"));
    }


    public class GeneralTab {

        public WebElement name() {
            return driver.findElement(By.name("name[en]"));
        }

        public WebElement code() {
            return driver.findElement(By.name("code"));
        }

        public void setStatus(Status status) {
            switch (status) {
                case ENABLED:
                    enabledStatus().click();
                    break;
                case DISABLED:
                    disabledStatus().click();
            }
        }

        private WebElement enabledStatus() {
            return driver.findElement(By.xpath("//label[contains(text(),'Enabled')]"));
        }

        private WebElement disabledStatus() {
            return driver.findElement(By.xpath("//label[contains(text(),'Disabled')]"));
        }

        public WebElement rootCategory() {
            return driver.findElement(By.cssSelector("[data-name='Root']"));
        }

        public void setCategory(String category) {
            driver.findElement(By.cssSelector(String.format("[data-name='%s']", category))).click();
        }

        public void setQuantity(String quantity) {
            quantityInput().clear();
            quantityInput().sendKeys(quantity);
        }

        private WebElement quantityInput() {
            return driver.findElement(By.name("quantity"));
        }

        public void uploadImage(String imagePath) {
            driver.findElement(By.name("new_images[]")).sendKeys(imagePath);
        }
    }


    public class InformationTab {

        private WebElement manufacturerSelect() {
            return driver.findElement(By.name("manufacturer_id"));
        }

        private WebElement shortDescriptionInput() {
            return driver.findElement(By.cssSelector("[name='short_description[en]']"));
        }

        public void selectManufacturer(String manufacturer) {
            manufacturerSelect().click();
            manufacturerSelect().findElement(By.xpath(String.format("./option[contains(text(), '%s')]", manufacturer))).click();
        }

        public void typeShortDescription(String shortDescription) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]",
                    shortDescriptionInput(), shortDescription);
        }
    }


    public class PricesTab {

        private WebElement purchasePriceInput() {
            return driver.findElement(By.name("purchase_price"));
        }

        private WebElement priceUSDInput() {
            return driver.findElement(By.name("prices[USD]"));
        }

        public void setPurchasePrice(String purchasePrice) {
            purchasePriceInput().clear();
            purchasePriceInput().sendKeys(purchasePrice);
        }

        public void setPriceUSD(String price) {
            priceUSDInput().clear();
            priceUSDInput().sendKeys(price);
        }
    }
}