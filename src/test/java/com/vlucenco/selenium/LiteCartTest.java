package com.vlucenco.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class LiteCartTest extends TestBase {

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost/litecart");
    }

    @Test
    public void testEachProductHasSticker() {
        List<WebElement> products = findElements(By.className("product"));
        for (WebElement product : products) {
            Assert.assertTrue("Number of stickers for the product not equal to 1",
                    product.findElements(By.className("sticker")).size() == 1);
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}