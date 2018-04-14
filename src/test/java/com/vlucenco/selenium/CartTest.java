package com.vlucenco.selenium;

import org.junit.Test;

public class CartTest extends TestBase {

    @Test
    public void testAddProductToCartThenRemoveItFromCart() {
        app.addProductsToCart(3);
        app.goToCart();
        app.removeAllProductsFromCart();
    }
}