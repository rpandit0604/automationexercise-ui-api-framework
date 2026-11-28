package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private final By signupLoginLink = By.cssSelector("a[href='/login']");
    private final By productsLink = By.cssSelector("a[href='/products']");
    private final By cartLink = By.cssSelector("a[href='/view_cart']");
    private final By logoutLink = By.cssSelector("a[href='/logout']");
    private final By loggedInUsername = By.xpath("//a[contains(text(),'Logged in as')]/b");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public LoginPage clickSignupLogin() {
        click(signupLoginLink);
        handleGoogleVignetteAd();
        return new LoginPage(driver);
    }

    public ProductsPage clickProducts() {
        click(productsLink);
        if (handleGoogleVignetteAd()) {
            click(productsLink);
        }
        return new ProductsPage(driver);
    }

    public CartPage clickCart() {
        click(cartLink);
        handleGoogleVignetteAd();
        return new CartPage(driver);
    }

    public boolean isUserLoggedIn() {
        return isDisplayed(loggedInUsername);
    }

    public String getLoggedInUsername() {
        return getText(loggedInUsername);
    }

    public LoginPage clickLogout() {
        click(logoutLink);
        handleGoogleVignetteAd();
        return new LoginPage(driver);
    }
}