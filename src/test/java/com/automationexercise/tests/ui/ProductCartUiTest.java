package com.automationexercise.tests.ui;

import com.automationexercise.pages.CartPage;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.ProductsPage;
import com.automationexercise.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductCartUiTest extends BaseTest {

    @Test(priority = 1, description = "TC_UI_03: Verify product search returns matching results")
    public void testSearchProduct() {
        HomePage homePage = new HomePage(getDriver());
        ProductsPage productsPage = homePage.clickProducts();

        Assert.assertTrue(productsPage.isAllProductsPageLoaded(), "All Products page did not load.");

        productsPage.searchProduct("Top");

        Assert.assertTrue(productsPage.isSearchedProductsVisible(), "Searched Products header not visible.");
        Assert.assertTrue(productsPage.getDisplayedProductsCount() > 0, "No products displayed for search term.");
    }

    @Test(priority = 2, description = "TC_UI_04: Verify adding a product to cart and viewing it in CartPage")
    public void testAddProductToCart() {
        HomePage homePage = new HomePage(getDriver());
        ProductsPage productsPage = homePage.clickProducts();

        CartPage cartPage = productsPage
                .addFirstProductToCart()
                .clickViewCartFromModal();

        Assert.assertTrue(cartPage.isCartPageLoaded(), "Cart page did not load.");
        Assert.assertTrue(cartPage.getCartItemsCount() > 0, "Cart is empty after adding product.");
        Assert.assertFalse(cartPage.getFirstCartProductName().isEmpty(), "Product name in cart is blank.");
    }
}