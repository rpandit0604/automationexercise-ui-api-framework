package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By loginEmailInput = By.cssSelector("input[data-qa='login-email']");
    private final By loginPasswordInput = By.cssSelector("input[data-qa='login-password']");
    private final By loginButton = By.cssSelector("button[data-qa='login-button']");
    private final By loginErrorMessage = By.cssSelector("form[action='/login'] p");
    private final By loginHeader = By.xpath("//h2[text()='Login to your account']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoginPageLoaded() {
        return isDisplayed(loginHeader);
    }

    public HomePage login(String email, String password) {
        type(loginEmailInput, email);
        type(loginPasswordInput, password);
        click(loginButton);
        handleGoogleVignetteAd();
        return new HomePage(driver);
    }

    public String getLoginErrorMessage() {
        return getText(loginErrorMessage);
    }
}