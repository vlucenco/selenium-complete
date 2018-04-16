package com.vlucenco.selenium.tests;

import org.junit.Test;

public class BrowserLogsTest extends TestBase {

    @Test
    public void testBrowserLogs() {
        app.admin().loginToAdminPanel();
        app.verifyBrowserHasNoLogs();
    }
}
