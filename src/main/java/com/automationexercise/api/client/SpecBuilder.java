package com.automationexercise.api.client;

import com.automationexercise.utils.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public final class SpecBuilder {

    private SpecBuilder() {
    }

    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.get("ui.api.url"))
                .setContentType(ContentType.URLENC)
                .log(LogDetail.METHOD)
                .log(LogDetail.URI)
                .build();
    }

    public static ResponseSpecification getResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .log(LogDetail.STATUS)
                .build();
    }
}