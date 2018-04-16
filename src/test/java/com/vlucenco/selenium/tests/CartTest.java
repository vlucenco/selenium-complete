package com.vlucenco.selenium.tests;

import org.junit.Test;

public class CartTest extends TestBase {

    @Test
    public void testAddProductToCartThenRemoveItFromCart() {
        app.customer().addProductsToCart(3);
        app.customer().goToCart();
        app.customer().removeAllProductsFromCart();
    }
}