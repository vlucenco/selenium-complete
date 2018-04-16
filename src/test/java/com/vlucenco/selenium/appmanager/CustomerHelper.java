package com.vlucenco.selenium.appmanager;

import com.vlucenco.selenium.model.Customer;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class CustomerHelper extends HelperBase {

    public CustomerHelper(ApplicationManager app) {
        super(app);
    }

    public void logout() {
        homePage.open();
        homePage.logoutButton().click();
    }

    public void login(String emailAddress, String password) {
        homePage.emailInput().sendKeys(emailAddress);
        homePage.passwordInput().sendKeys(password);
        homePage.loginButton().click();
    }

    public void goToCart() {
        productPage.checkoutButton().click();
        cartPage.productShortcut().click();
    }

    private void selectSizeIfRequired() {
        if (isElementPresent(productPage.sizeOptionSelector)) {
            findElement(productPage.sizeOptionSelector).findElement(productPage.smallSize).click();
        }
    }

    public void addProductsToCart(Integer numOfProducts) {
        homePage.open();
        for (int i = 0; i < numOfProducts; i++) {
            addProduct();
        }
    }

    private void addProduct() {
        homePage.mostPopularProduct().click();
        Integer cartQuantity = Integer.valueOf(productPage.cartQuantityElement().getText());
        selectSizeIfRequired();
        productPage.addToCartButton().click();
        wait.until(textToBePresentInElement(productPage.cartQuantityElement(), String.valueOf(cartQuantity + 1)));
        productPage.homePageButton().click();
    }

    public void removeAllProductsFromCart() {
        Integer productsCount = cartPage.productsNumber();
        for (int i = 0; i < productsCount; i++) {
            removeProduct();
        }
    }

    private void removeProduct() {
        wait.until(visibilityOfElementLocated(cartPage.removeButton)).click();
        wait.until(stalenessOf(cartPage.orderSummaryTable()));
    }

    public void submitAccountCreationForm(Customer customer) {
        registrationPage.open();
        registrationPage.firstnameInput().sendKeys(customer.getFirstname());
        registrationPage.lastnameInput().sendKeys(customer.getLastname());
        registrationPage.address1Input().sendKeys(customer.getAddress());
        registrationPage.postcodeInput().sendKeys(customer.getPostcode());
        registrationPage.cityInput().sendKeys(customer.getCity());
        registrationPage.selectCountry(customer.getCountry());
        registrationPage.selectZone(customer.getZone());

        registrationPage.emailInput().sendKeys(customer.getEmail());
        registrationPage.phoneInput().sendKeys(customer.getPhone());
        registrationPage.passwordInput().sendKeys(customer.getPassword());
        registrationPage.confirmedPasswordInput().sendKeys(customer.getPassword());
        registrationPage.createAccountButton().click();
    }
}