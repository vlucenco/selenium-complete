package com.vlucenco.selenium;

import org.junit.Test;

public class BrowserLogsTest extends TestBase {

    @Test
    public void testBrowserLogs() {
        app.loginToAdminPanel();
        app.verifyBrowserHasNoLogs();
    }
}
