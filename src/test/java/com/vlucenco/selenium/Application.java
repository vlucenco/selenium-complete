package com.vlucenco.selenium;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class Application {

    private WebDriverWait wait;
    private WebDriver driver;

    public Application() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    public void quit() {
        driver.quit();
    }

    /* ----------------CustomerRegistrationTests-----------------*/

    public Set<String> getCustomerIds() {
        loginToAdminPanel();

        driver.get("http://localhost/litecart/admin/?app=customers&doc=customers");
        return driver.findElements(By.cssSelector("table.dataTable tr.row")).stream()
                .map(e -> e.findElements(By.tagName("td")).get(2).getText())
                .collect(toSet());
    }

    public void submitAccountCreationForm(Customer customer) {
        driver.get("http://localhost/litecart/en/create_account");
        populateField(By.name("firstname"), customer.getFirstname());
        populateField(By.name("lastname"), customer.getLastname());
        populateField(By.name("address1"), customer.getAddress());
        populateField(By.name("postcode"), customer.getPostcode());
        populateField(By.name("city"), customer.getCity());

        click(By.className("selection"));
        click(By.xpath(String.format("//li[contains(text(), '%s')]", customer.getCountry())));
        wait.until((WebDriver d) -> d.findElement(
                By.cssSelector(String.format("select[name=zone_code] option[value=%s]", customer.getZone()))));
        new Select(driver.findElement(By.cssSelector("select[name=zone_code]"))).selectByValue(customer.getZone());

        populateField(By.name("email"), customer.getEmail());
        populateField(By.name("phone"), customer.getPhone());
        populateField(By.name("password"), customer.getPassword());
        populateField(By.name("confirmed_password"), customer.getPassword());
        click(By.name("create_account"));
    }

    public void customerLogout() {
        driver.get("http://localhost/litecart");
        click(By.linkText("Logout"));
    }

    public void customerLogin(String emailAddress, String password) {
        populateField(By.name("email"), emailAddress);
        populateField(By.name("password"), password);
        click(By.name("login"));
    }
    /* ----------------AdminPanelTest-----------------*/

    public final String COUNTRIES_PAGE_URL = "http://localhost/litecart/admin/?app=countries&doc=countries";
    public final String GEO_ZONES_PAGE_URL = "http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones";

    private By menuItemLocator = By.id("app-");
    private By subMenuItemLocator = By.cssSelector("#app-.selected li");

    public void verifyHeadersOnAllAdminPages() {
        for (int menuIndex = 0; menuIndex < findElements(menuItemLocator).size(); menuIndex++) {
            checkPageHeader(menuIndex, menuItemLocator);

            for (int subMenuIndex = 0; subMenuIndex < findElements(subMenuItemLocator).size(); subMenuIndex++) {
                checkPageHeader(subMenuIndex, subMenuItemLocator);
            }
        }
    }

    private void checkPageHeader(int index, By locator) {
        List<WebElement> elements = findElements(locator);
        elements.get(index).click();
        Assert.assertTrue(findElement(By.tagName("h1")).isDisplayed());
    }

    public List<WebElement> goToPageAndGetCountries(String page, By locator) {
        driver.get(page);
        return findElements(locator);
    }

    public boolean countryHasZones(WebElement element) {
        try {
            int zonesNum = Integer.parseInt(element.findElement(By.xpath("./td[6]")).getText());
            return zonesNum > 0;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void getCountryZonesAndVerifySorting(By locator) {
        List<WebElement> zones = findElements(locator);
        List<String> actuallySortedZones = new ArrayList<>();
        zones.forEach(zone -> actuallySortedZones.add(zone.getText()));
        verifyCollectionSorted(actuallySortedZones);
    }

    public void verifyCollectionSorted(List<String> actuallySortedElements) {
        List<String> expectedAlphabeticallySortedElements = new ArrayList<>(actuallySortedElements)
                .stream().sorted().collect(Collectors.toList());
        Assert.assertEquals("Elements sorting not alphabetical",
                expectedAlphabeticallySortedElements, actuallySortedElements);
    }

    /*-----------------AddNewProductTest-----------------*/

    public String productName;
    public String imagePath;
    private String shortDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse sollicitudin ante massa, eget ornare libero porta congue.";

    public void goToAddNewProductPage() {
        click(By.xpath("//span[contains(text(),'Catalog')]"));
        click(By.linkText("Add New Product"));
    }

    public void populateGeneralTab() {
        click(By.xpath("//label[contains(text(),'Enabled')]"));
        populateField(By.name("name[en]"), productName);
        populateField(By.name("code"), "wd001");
        findElement(By.cssSelector("[data-name='Root']")).click();
        findElement(By.cssSelector("[data-name='Rubber Ducks']")).click();
        populateField(By.name("quantity"), "50.00");
        populateField(By.name("new_images[]"), imagePath);
    }

    public void populateInformationTab() {
        click(By.xpath("//a[contains(text(), 'Information')]"));
        click(By.name("manufacturer_id"));
        click(By.cssSelector("select[name=manufacturer_id] option[value='1']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]",
                findElement(By.cssSelector("[name='short_description[en]']")), shortDescription);
    }

    public void populatePricesTab() {
        click(By.xpath("//a[contains(text(), 'Prices')]"));
        populateField(By.name("purchase_price"), "100");
        populateField(By.name("prices[USD]"), "20.000");
    }

    public void saveProduct() {
        click(By.name("save"));
    }

    public void verifyProductWasAddedToCatalog() {
        click(By.xpath("//span[contains(text(),'Catalog')]"));
        click(By.xpath("//a[contains(text(),'Rubber Ducks')]"));
        Assert.assertTrue(findElement(By.linkText(productName)).isDisplayed());
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

    /* ---------------CartTest------------------*/

    public void addProductsToCart(Integer numOfProducts) {
        driver.get("http://localhost/litecart");
        for (int i = 0; i < numOfProducts; i++) {
            addProduct();
        }
    }

    private void addProduct() {
        click(By.cssSelector("#box-most-popular li"));
        WebElement cartQuantityElement = findElement(By.cssSelector("span[class='quantity']"));
        Integer cartQuantity = Integer.valueOf(cartQuantityElement.getText());
        selectSizeIfRequired();
        click(By.name("add_cart_product"));
        wait.until(textToBePresentInElement(cartQuantityElement, String.valueOf(cartQuantity + 1)));
        click(By.className("fa-home"));
    }

    private void selectSizeIfRequired() {
        By sizeOptionLocator = By.xpath("//select[@name='options[Size]']");
        if (isElementPresent(sizeOptionLocator)) {
            findElement(sizeOptionLocator).findElement(By.xpath("./option[2]")).click();
        }
    }

    public void goToCart() {
        click(By.linkText("Checkout »"));
        click(By.cssSelector(".shortcut"));
    }

    public void removeAllProductsFromCart() {
        Integer productsCount = findElements(By.cssSelector(".shortcuts li")).size();
        for (int i = 0; i < productsCount; i++) {
            removeProduct();
        }
    }

    private void removeProduct() {
        wait.until(visibilityOfElementLocated(By.name("remove_cart_item"))).click();
        wait.until(stalenessOf(findElement(By.className("dataTable"))));
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

    void loginToAdminPanel() {
        driver.get("http://localhost/litecart/admin/");
        if (!isElementPresent(By.className("fa-sign-out"))) {
            findElement(By.name("username")).sendKeys("admin");
            findElement(By.name("password")).sendKeys("admin");
            findElement(By.name("login")).click();
            wait.until(driver -> findElement(By.className("fa-sign-out")));
        }
    }

    void click(By locator) {
        driver.findElement(locator).click();
    }

    WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    boolean isElementPresent(By locator) {
        try {
            findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    void populateField(By fieldLocator, String input) {
        findElement(fieldLocator).clear();
        findElement(fieldLocator).sendKeys(input);
    }

    String anyWindowsOtherThan(Set<String> oldWindows) {
        Set<String> handles = driver.getWindowHandles();
        handles.removeAll(oldWindows);
        return handles.size() > 0 ? handles.iterator().next() : null;
    }
}