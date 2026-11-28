package com.automationexercise.tests.ui;

import com.automationexercise.api.models.User;
import com.automationexercise.api.services.UserService;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.LoginPage;
import com.automationexercise.base.BaseTest;
import net.datafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginUiTest extends BaseTest {

    private UserService userService;
    private User testUser;

    @BeforeClass(alwaysRun = true)
    public void createTestUserViaApi() {
        userService = new UserService();
        Faker faker = new Faker();

        testUser = new User();
        testUser.setName(faker.name().firstName());
        testUser.setEmail("ui_test_" + System.currentTimeMillis() + "@testmail.com");
        testUser.setPassword("Test@1234");
        testUser.setTitle("Mr");
        testUser.setBirthDate("10");
        testUser.setBirthMonth("May");
        testUser.setBirthYear("1995");
        testUser.setFirstname(testUser.getName());
        testUser.setLastname(faker.name().lastName());
        testUser.setCompany("TestCorp");
        testUser.setAddress1("123 Main Street");
        testUser.setAddress2("Sector 4");
        testUser.setCountry("India");
        testUser.setZipcode("827001");
        testUser.setState("Jharkhand");
        testUser.setCity("Bokaro");
        testUser.setMobileNumber("9876543210");

        int statusCode = userService.createAccount(testUser).jsonPath().getInt("responseCode");
        Assert.assertEquals(statusCode, 201, "Pre-condition failed: Test user could not be created via API.");
    }

    @Test(priority = 1, description = "TC_UI_01: Verify user can login with valid credentials")
    public void testValidLogin() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage = homePage.clickSignupLogin();

        Assert.assertTrue(loginPage.isLoginPageLoaded(), "Login page did not load.");

        HomePage loggedInHome = loginPage.login(testUser.getEmail(), testUser.getPassword());

        Assert.assertTrue(loggedInHome.isUserLoggedIn(), "User is not logged in after valid login.");
        Assert.assertEquals(loggedInHome.getLoggedInUsername(), testUser.getName(),
                "Logged-in username mismatch.");
    }

    @Test(priority = 2, description = "TC_UI_02: Verify error message on invalid login credentials")
    public void testInvalidLogin() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage = homePage.clickSignupLogin();

        loginPage.login("invalid_ui_user@testmail.com", "WrongPass@123");

        String errorMsg = loginPage.getLoginErrorMessage();
        Assert.assertEquals(errorMsg, "Your email or password is incorrect!",
                "Expected invalid login error message did not match.");
    }

    @AfterClass(alwaysRun = true)
    public void deleteTestUserViaApi() {
        if (testUser != null) {
            userService.deleteAccount(testUser.getEmail(), testUser.getPassword());
        }
    }
}