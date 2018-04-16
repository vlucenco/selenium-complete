package com.vlucenco.selenium.appmanager;

import java.util.Set;

public class AdminHelper extends HelperBase {

    public AdminHelper(ApplicationManager app) {
        super(app);
    }

    public void loginToAdminPanel() {
        adminPanelLoginPage.open();
        if (!isElementPresent(adminPanelLoginPage.signOutButton)) {
            adminPanelLoginPage.usernameInput().sendKeys("admin");
            adminPanelLoginPage.passwordInput().sendKeys("admin");
            adminPanelLoginPage.loginButton().click();
            wait.until(driver -> findElement(adminPanelLoginPage.signOutButton));
        }
    }

    public Set<String> getCustomerIds() {
        loginToAdminPanel();
        customerListPage.open();
        return customerListPage.getCustomerIds();
    }
}
