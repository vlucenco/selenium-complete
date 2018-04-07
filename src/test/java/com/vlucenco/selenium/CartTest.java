package com.vlucenco.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class CartTest extends TestBase {

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost/litecart");
    }

    @Test
    public void testAddProductToCartThenRemoveItFromCart() throws InterruptedException {
        addProductsToCart(3);
        goToCart();
        removeAllProductsFromCart();
    }

    private void addProductsToCart(Integer numOfProducts) {
        for (int i = 0; i < numOfProducts; i++) {
            addProduct();
        }
    }

    private void addProduct() {
        click(By.cssSelector("#box-most-popular li"));
        WebElement cartQuantityElement = findElement(By.cssSelector("span[class='quantity']"));
        Integer cartQuantity = Integer.valueOf(cartQuantityElement.getText());
        selectSizeIfRequired();
        click(By.name("add_cart_product"));
        wait.until(textToBePresentInElement(cartQuantityElement, String.valueOf(cartQuantity + 1)));
        click(By.className("fa-home"));
    }

    private void selectSizeIfRequired() {
        By sizeOptionLocator = By.xpath("//select[@name='options[Size]']");
        if (isElementPresent(sizeOptionLocator)) {
            findElement(sizeOptionLocator).findElement(By.xpath("./option[2]")).click();
        }
    }

    private void goToCart() {
        click(By.linkText("Checkout »"));
        click(By.cssSelector(".shortcut"));
    }

    private void removeAllProductsFromCart() {
        Integer productsCount = findElements(By.cssSelector(".shortcuts li")).size();
        for (int i = 0; i < productsCount; i++) {
            removeProduct();
        }
    }

    private void removeProduct() {
        wait.until(visibilityOfElementLocated(By.name("remove_cart_item"))).click();
        wait.until(stalenessOf(findElement(By.className("dataTable"))));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}