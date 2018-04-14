package com.vlucenco.selenium;

import org.junit.Test;

public class ExternalLinksTest extends TestBase {

    @Test
    public void testExternalLinksOpenInNewWindow() {
        app.loginToAdminPanel();
        app.verifyExternalLinksOpenInNewWindow();
    }
}