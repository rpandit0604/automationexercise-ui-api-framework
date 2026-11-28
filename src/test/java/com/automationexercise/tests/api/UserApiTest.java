package com.automationexercise.tests.api;

import com.automationexercise.api.models.ApiResponse;
import com.automationexercise.api.models.User;
import com.automationexercise.api.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserApiTest {

    private final UserService userService = new UserService();
    private final ObjectMapper mapper = new ObjectMapper();
    private User testUser;

    @BeforeClass
    public void setupTestUser() {
        Faker faker = new Faker();
        testUser = new User();
        testUser.setName(faker.name().fullName());
        testUser.setEmail("aeq_" + System.currentTimeMillis() + "@testmail.com");
        testUser.setPassword("Pass@12345");
        testUser.setTitle("Mr");
        testUser.setBirthDate("10");
        testUser.setBirthMonth("May");
        testUser.setBirthYear("1995");
        testUser.setFirstname(faker.name().firstName());
        testUser.setLastname(faker.name().lastName());
        testUser.setCompany("QA Corp");
        testUser.setAddress1(faker.address().streetAddress());
        testUser.setAddress2("Sector 4");
        testUser.setCountry("India");
        testUser.setZipcode("827004");
        testUser.setState("Jharkhand");
        testUser.setCity("Bokaro");
        testUser.setMobileNumber("9876543210");
    }

    @Test(priority = 1, description = "TC_API_05: Create new user account via API")
    public void testCreateAccount() throws Exception {
        Response response = userService.createAccount(testUser);
        ApiResponse apiRes = mapper.readValue(response.asString(), ApiResponse.class);
        Assert.assertEquals(apiRes.getResponseCode(), 201);
        Assert.assertEquals(apiRes.getMessage(), "User created!");
    }

    @Test(priority = 2, dependsOnMethods = "testCreateAccount", description = "TC_API_06: Verify login with valid credentials")
    public void testVerifyLoginValid() throws Exception {
        Response response = userService.verifyLogin(testUser.getEmail(), testUser.getPassword());
        ApiResponse apiRes = mapper.readValue(response.asString(), ApiResponse.class);
        Assert.assertEquals(apiRes.getResponseCode(), 200);
        Assert.assertEquals(apiRes.getMessage(), "User exists!");
    }

    @Test(priority = 3, description = "TC_API_07: Verify login with invalid credentials returns 404 (Negative)")
    public void testVerifyLoginInvalid() throws Exception {
        Response response = userService.verifyLogin("invalid_user@testmail.com", "WrongPass");
        ApiResponse apiRes = mapper.readValue(response.asString(), ApiResponse.class);
        Assert.assertEquals(apiRes.getResponseCode(), 404);
        Assert.assertEquals(apiRes.getMessage(), "User not found!");
    }

    @Test(priority = 4, dependsOnMethods = "testCreateAccount", description = "TC_API_08: Delete created user account (Cleanup)")
    public void testDeleteAccount() throws Exception {
        Response response = userService.deleteAccount(testUser.getEmail(), testUser.getPassword());
        ApiResponse apiRes = mapper.readValue(response.asString(), ApiResponse.class);
        Assert.assertEquals(apiRes.getResponseCode(), 200);
        Assert.assertEquals(apiRes.getMessage(), "Account deleted!");
    }
}