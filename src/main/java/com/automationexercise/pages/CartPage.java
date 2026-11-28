package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {

    private final By cartTable = By.id("cart_info_table");
    private final By cartRows = By.cssSelector("#cart_info_table tbody tr");
    private final By firstProductName = By.cssSelector("#cart_info_table tbody tr:first-child .cart_description h4 a");
    private final By proceedToCheckoutBtn = By.cssSelector("a.check_out");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCartPageLoaded() {
        return isDisplayed(cartTable);
    }

    public int getCartItemsCount() {
        if (!isDisplayed(cartTable)) {
            return 0;
        }
        List<WebElement> rows = driver.findElements(cartRows);
        return rows.size();
    }

    public String getFirstCartProductName() {
        return getText(firstProductName);
    }

    public void clickProceedToCheckout() {
        click(proceedToCheckoutBtn);
    }
}