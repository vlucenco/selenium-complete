package com.vlucenco.selenium.tests;

import org.junit.Test;

public class ExternalLinksTest extends TestBase {

    @Test
    public void testExternalLinksOpenInNewWindow() {
        app.admin().loginToAdminPanel();
        app.verifyExternalLinksOpenInNewWindow();
    }
}