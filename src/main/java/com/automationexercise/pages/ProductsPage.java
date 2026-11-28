package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductsPage extends BasePage {

    private final By allProductsHeader = By.xpath("//h2[text()='All Products']");
    private final By searchInput = By.id("search_product");
    private final By searchButton = By.id("submit_search");
    private final By searchedProductsHeader = By.xpath("//h2[text()='Searched Products']");
    private final By productCards = By.cssSelector(".features_items .product-image-wrapper");
    private final By firstProductAddToCartBtn = By.xpath("(//div[@class='productinfo text-center']//a[contains(@class,'add-to-cart')])[1]");
    private final By viewCartModalLink = By.xpath("//div[@id='cartModal']//a[@href='/view_cart']");
    private final By continueShoppingBtn = By.xpath("//button[text()='Continue Shopping']");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAllProductsPageLoaded() {
        return isDisplayed(allProductsHeader);
    }

    public ProductsPage searchProduct(String productName) {
        type(searchInput, productName);
        click(searchButton);
        return this;
    }

    public boolean isSearchedProductsVisible() {
        return isDisplayed(searchedProductsHeader);
    }

    public int getDisplayedProductsCount() {
        waitForVisible(productCards);
        List<WebElement> products = driver.findElements(productCards);
        return products.size();
    }

    public ProductsPage addFirstProductToCart() {
        scrollIntoView(firstProductAddToCartBtn);
        click(firstProductAddToCartBtn);
        return this;
    }

    public CartPage clickViewCartFromModal() {
        click(viewCartModalLink);
        handleGoogleVignetteAd();
        return new CartPage(driver);
    }

    public ProductsPage clickContinueShopping() {
        click(continueShoppingBtn);
        return this;
    }
}