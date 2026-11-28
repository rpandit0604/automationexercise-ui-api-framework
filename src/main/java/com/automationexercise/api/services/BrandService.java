package com.automationexercise.api.services;

import com.automationexercise.api.client.SpecBuilder;
import com.automationexercise.api.endpoints.Routes;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BrandService {

    public Response getAllBrands() {
        return given()
                .spec(SpecBuilder.getRequestSpec())
        .when()
                .get(Routes.BRANDS_LIST)
        .then()
                .spec(SpecBuilder.getResponseSpec())
                .extract().response();
    }
}