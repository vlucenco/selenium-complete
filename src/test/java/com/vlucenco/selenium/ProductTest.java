package com.vlucenco.selenium;

import org.junit.Test;

public class ProductTest extends TestBase {

    @Test
    public void testEachProductHasSticker() {
        app.verifyEachProductHasSticker();
    }

    @Test
    public void testSelectProductOnMainPageLeadsToCorrectProductPage() {
        app.verifyProductOnMainPageLeadsToCorrectProductPage();
    }
}