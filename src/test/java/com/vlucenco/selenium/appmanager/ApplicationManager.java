package com.vlucenco.selenium.appmanager;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class ApplicationManager {

    WebDriverWait wait;
    WebDriver driver;

    private ProductHelper productHelper;
    private CustomerHelper customerHelper;
    private AdminHelper adminHelper;
    private NavigationHelper navigationHelper;

    public ApplicationManager() {
    }

    public void init() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        productHelper = new ProductHelper(this);
        customerHelper = new CustomerHelper(this);
        adminHelper = new AdminHelper(this);
        navigationHelper = new NavigationHelper(this);
    }

    public void stop() {
        driver.quit();
    }

    public ProductHelper product() {
        return productHelper;
    }

    public CustomerHelper customer() {
        return customerHelper;
    }

    public AdminHelper admin() {
        return adminHelper;
    }

    public NavigationHelper goTo() {
        return navigationHelper;
    }

    /* --------------BrowserLogsTest-------------------*/

    public void verifyBrowserHasNoLogs() {
        click(By.xpath("//span[contains(text(),'Catalog')]"));
        click(By.cssSelector(".fa-folder+a"));
        By productLocator = By.cssSelector(".dataTable img+a");

        List<WebElement> products = findElements(productLocator);
        for (int i = 0; i < products.size(); i++) {
            products.get(i).click();
            List<LogEntry> logs = driver.manage().logs().get("browser").getAll();
            Assert.assertTrue(logs.size() == 0);
            click(By.name("cancel"));
            products = findElements(productLocator);
        }
    }

    /* ---------------ExternalLinksTest------------------*/

    public void verifyExternalLinksOpenInNewWindow() {
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

    /* --------------ProductTest-------------------*/

    private final By regularPrice = By.cssSelector("s[class=regular-price]");
    private final By campaignPrice = By.cssSelector("strong[class=campaign-price]");

    public void verifyEachProductHasSticker() {
        driver.get("http://localhost/litecart");
        List<WebElement> products = findElements(By.className("product"));
        for (WebElement product : products) {
            Assert.assertTrue("Number of stickers for the product not equal to 1",
                    product.findElements(By.className("sticker")).size() == 1);
        }
    }

    public void verifyProductOnMainPageLeadsToCorrectProductPage() {
        driver.get("http://localhost/litecart");
        // Verifications on Main Page
        WebElement product = findElement(By.cssSelector("#box-campaigns li"));

        verifyProductRegularPriceIsGrey(product);
        verifyProductCampaignPriceIsRed(product);

        Double mainPageRegularPriceSize = getElementFontSize(product.findElement(regularPrice));
        Double mainPageCampaignPriceSize = getElementFontSize(product.findElement(campaignPrice));
        Assert.assertTrue("Campaign price should be bigger than regular price", mainPageRegularPriceSize < mainPageCampaignPriceSize);

        String mainPageProductName = product.findElement(By.className("name")).getText();
        String mainPageRegularPrice = product.findElement(regularPrice).getText();
        String mainPageCampaignPrice = product.findElement(campaignPrice).getText();

        product.click();

        //Verifications on Product page
        verifyProductRegularPriceIsGrey(findElement(By.id("box-product")));
        verifyProductCampaignPriceIsRed(findElement(By.id("box-product")));

        Double productPageRegularPriceSize = getElementFontSize(findElement(regularPrice));
        Double productPageCampaignPriceSize = getElementFontSize(findElement(campaignPrice));
        Assert.assertTrue("Campaign price should be bigger than regular price", productPageRegularPriceSize < productPageCampaignPriceSize);


        String productPageName = findElement(By.tagName("h1")).getText();
        String productPageRegularPrice = findElement(regularPrice).getText();
        String productPageCampaignPrice = findElement(campaignPrice).getText();

        Assert.assertEquals(mainPageProductName, productPageName);
        Assert.assertEquals(mainPageRegularPrice, productPageRegularPrice);
        Assert.assertEquals(mainPageCampaignPrice, productPageCampaignPrice);
    }

    private Double getElementFontSize(WebElement product) {
        String sizeStr = product.getCssValue("font-size");
        return Double.valueOf(sizeStr.replaceAll("px", ""));
    }

    private void verifyProductRegularPriceIsGrey(WebElement product) {
        String[] regularPriceRGB = getElementRGBValues(product.findElement(regularPrice));
        Assert.assertTrue(regularPriceRGB[0].equals(regularPriceRGB[2]) && regularPriceRGB[1].equals(regularPriceRGB[2]));
    }

    private void verifyProductCampaignPriceIsRed(WebElement product) {
        String[] campaignPriceRGB = getElementRGBValues(product.findElement(campaignPrice));
        Assert.assertTrue(!campaignPriceRGB[0].equals(campaignPriceRGB[2]) && campaignPriceRGB[1].equals(campaignPriceRGB[2]));
    }

    private String[] getElementRGBValues(WebElement element) {
        String colorStyle = element.getCssValue("color");
        return colorStyle.replaceAll("\\D", " ").trim().split("\\s+");
    }

    /* ---------------------------------*/

    private void click(By locator) {
        driver.findElement(locator).click();
    }

    private WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    private List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    private String anyWindowsOtherThan(Set<String> oldWindows) {
        Set<String> handles = driver.getWindowHandles();
        handles.removeAll(oldWindows);
        return handles.size() > 0 ? handles.iterator().next() : null;
    }
}