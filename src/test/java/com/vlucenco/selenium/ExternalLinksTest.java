package com.vlucenco.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class ExternalLinksTest extends TestBase {

    @Test
    public void testExternalLinksOpenInNewWindow() {
        loginToAdminPanel();
        click(By.linkText("Countries"));
        click(By.className("fa-pencil"));
        String mainWindow = driver.getWindowHandle();
        Set<String> oldWindows = driver.getWindowHandles();

        for (WebElement link : findElements(By.className("fa-external-link"))) {
            link.click();
            verifyNewWindowOpens(oldWindows);
            driver.switchTo().window(mainWindow);
        }
    }

    private void verifyNewWindowOpens(Set<String> oldWindows) {
        String newWindow = wait.until(driver -> anyWindowsOtherThan(oldWindows));
        driver.switchTo().window(newWindow);
        wait.until(visibilityOf(findElement(By.tagName("h1"))));
        driver.close();
    }
}