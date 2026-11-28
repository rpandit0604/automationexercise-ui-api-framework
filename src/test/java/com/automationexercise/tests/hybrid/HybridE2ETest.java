package com.automationexercise.tests.hybrid;

import com.automationexercise.api.models.User;
import com.automationexercise.api.services.UserService;
import com.automationexercise.pages.CartPage;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.LoginPage;
import com.automationexercise.pages.ProductsPage;
import com.automationexercise.base.BaseTest;
import net.datafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class HybridE2ETest extends BaseTest {

    private UserService userService;
    private User dynamicUser;

    @Test(description = "TC_E2E_01: Create user via API, login and add product to cart via UI, then cleanup via API")
    public void testHybridUserJourney() {
        // 1. API Layer: Fast test data creation
        userService = new UserService();
        Faker faker = new Faker();

        dynamicUser = new User();
        dynamicUser.setName(faker.name().firstName());
        dynamicUser.setEmail("e2e_" + System.currentTimeMillis() + "@testmail.com");
        dynamicUser.setPassword("Hybrid@123");
        dynamicUser.setTitle("Mr");
        dynamicUser.setBirthDate("15");
        dynamicUser.setBirthMonth("August");
        dynamicUser.setBirthYear("1995");
        dynamicUser.setFirstname(dynamicUser.getName());
        dynamicUser.setLastname(faker.name().lastName());
        dynamicUser.setCompany("HybridTech");
        dynamicUser.setAddress1("Sector 4");
        dynamicUser.setAddress2("Main Road");
        dynamicUser.setCountry("India");
        dynamicUser.setZipcode("827001");
        dynamicUser.setState("Jharkhand");
        dynamicUser.setCity("Bokaro");
        dynamicUser.setMobileNumber("9876543210");

        int createStatus = userService.createAccount(dynamicUser).jsonPath().getInt("responseCode");
        Assert.assertEquals(createStatus, 201, "API user creation failed.");

        // 2. UI Layer: Login -> Search -> Add to Cart -> Verify Cart
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage = homePage.clickSignupLogin();
        HomePage loggedInHome = loginPage.login(dynamicUser.getEmail(), dynamicUser.getPassword());

        Assert.assertTrue(loggedInHome.isUserLoggedIn(), "UI login failed for API-created user.");
        Assert.assertEquals(loggedInHome.getLoggedInUsername(), dynamicUser.getName(), "Username mismatch.");

        ProductsPage productsPage = loggedInHome.clickProducts();
        CartPage cartPage = productsPage
                .searchProduct("Blue Top")
                .addFirstProductToCart()
                .clickViewCartFromModal();

        Assert.assertTrue(cartPage.isCartPageLoaded(), "Cart page did not load.");
        Assert.assertTrue(cartPage.getCartItemsCount() > 0, "No items found in cart.");
        Assert.assertTrue(cartPage.getFirstCartProductName().toLowerCase().contains("blue top"),
                "Expected searched product 'Blue Top' in cart.");
    }

    // 3. API Layer: Guaranteed cleanup even if UI assertion fails
    @AfterMethod(alwaysRun = true)
    public void cleanupUserViaApi() {
        if (dynamicUser != null && userService != null) {
            int deleteStatus = userService.deleteAccount(dynamicUser.getEmail(), dynamicUser.getPassword())
                    .jsonPath().getInt("responseCode");
            log.info("API cleanup deleteAccount responseCode: {}", deleteStatus);
        }
    }
}