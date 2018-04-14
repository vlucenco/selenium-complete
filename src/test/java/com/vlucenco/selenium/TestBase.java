package com.vlucenco.selenium;

import org.junit.Before;

class TestBase {

    private static ThreadLocal<Application> tlApp = new ThreadLocal<>();
    public Application app;

    @Before
    public void start() {
        if (tlApp.get() != null) {
            app = tlApp.get();
            return;
        }

        app = new Application();
        tlApp.set(app);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            app.quit();
            app = null;
        }));
    }
}