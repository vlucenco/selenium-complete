package com.vlucenco.selenium.tests;

import com.vlucenco.selenium.appmanager.ApplicationManager;
import org.junit.After;
import org.junit.Before;

class TestBase {

    public static final ApplicationManager app = new ApplicationManager();

    @Before
    public void start() {
        app.init();
    }

    @After
    public void stop() {
        app.stop();
    }
}