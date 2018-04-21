package com.vlucenco.selenium.appmanager;

public class NavigationHelper extends HelperBase{

    NavigationHelper(ApplicationManager app) {
        super(app);
    }

    public void homePage() {
        homePage.open();
    }

    public void customerRegistrationPage() {
        registrationPage.open();
    }

    public void countriesAdminPage() {
        countriesAdminPage.open();
    }

    public void geoZonesAdminPage() {
        geoZonesAdminPage.open();
    }

    public void newProductPage() {
        newProductPage.open();
    }
}
