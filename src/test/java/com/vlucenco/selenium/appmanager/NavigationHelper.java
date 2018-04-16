package com.vlucenco.selenium.appmanager;

public class NavigationHelper extends HelperBase{

    public NavigationHelper(ApplicationManager app) {
        super(app);
    }

    public void homePage() {
        homePage.open();
    }

    public void customerRegistrationPage() {
        registrationPage.open();
    }
}
