package com.automationexercise.api.services;

import com.automationexercise.api.client.SpecBuilder;
import com.automationexercise.api.endpoints.Routes;
import com.automationexercise.api.models.User;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserService {

    public Response createAccount(User user) {
        return given()
                .spec(SpecBuilder.getRequestSpec())
                .formParams(user.toFormParams())
        .when()
                .post(Routes.CREATE_ACCOUNT)
        .then()
                .spec(SpecBuilder.getResponseSpec())
                .extract().response();
    }

    public Response verifyLogin(String email, String password) {
        return given()
                .spec(SpecBuilder.getRequestSpec())
                .formParam("email", email)
                .formParam("password", password)
        .when()
                .post(Routes.VERIFY_LOGIN)
        .then()
                .spec(SpecBuilder.getResponseSpec())
                .extract().response();
    }

    public Response getUserByEmail(String email) {
        return given()
                .spec(SpecBuilder.getRequestSpec())
                .queryParam("email", email)
        .when()
                .get(Routes.GET_USER_BY_EMAIL)
        .then()
                .spec(SpecBuilder.getResponseSpec())
                .extract().response();
    }

    public Response deleteAccount(String email, String password) {
        return given()
                .spec(SpecBuilder.getRequestSpec())
                .formParam("email", email)
                .formParam("password", password)
        .when()
                .delete(Routes.DELETE_ACCOUNT)
        .then()
                .spec(SpecBuilder.getResponseSpec())
                .extract().response();
    }
}