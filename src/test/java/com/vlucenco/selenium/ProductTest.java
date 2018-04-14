package com.vlucenco.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductTest extends TestBase {

    private static final By regularPrice = By.cssSelector("s[class=regular-price]");
    private static final By campaignPrice = By.cssSelector("strong[class=campaign-price]");

    @Test
    public void testEachProductHasSticker() {
        driver.get("http://localhost/litecart");
        List<WebElement> products = findElements(By.className("product"));
        for (WebElement product : products) {
            Assert.assertTrue("Number of stickers for the product not equal to 1",
                    product.findElements(By.className("sticker")).size() == 1);
        }
    }

    @Test
    public void testSelectProductOnMainPageLeadsToCorrectProductPage() {
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
}