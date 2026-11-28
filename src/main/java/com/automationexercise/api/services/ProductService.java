package com.automationexercise.api.services;

import com.automationexercise.api.client.SpecBuilder;
import com.automationexercise.api.endpoints.Routes;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ProductService {

    public Response getAllProducts() {
        return given()
                .spec(SpecBuilder.getRequestSpec())
        .when()
                .get(Routes.PRODUCTS_LIST)
        .then()
                .spec(SpecBuilder.getResponseSpec())
                .extract().response();
    }

    public Response searchProduct(String searchTerm) {
        return given()
                .spec(SpecBuilder.getRequestSpec())
                .formParam("search_product", searchTerm)
        .when()
                .post(Routes.SEARCH_PRODUCT)
        .then()
                .spec(SpecBuilder.getResponseSpec())
                .extract().response();
    }

    public Response searchProductWithoutParam() {
        return given()
                .spec(SpecBuilder.getRequestSpec())
        .when()
                .post(Routes.SEARCH_PRODUCT)
        .then()
                .spec(SpecBuilder.getResponseSpec())
                .extract().response();
    }
}