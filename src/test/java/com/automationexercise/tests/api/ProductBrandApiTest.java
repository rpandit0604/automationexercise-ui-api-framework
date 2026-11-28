package com.automationexercise.tests.api;

import com.automationexercise.api.models.ApiResponse;
import com.automationexercise.api.services.BrandService;
import com.automationexercise.api.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ProductBrandApiTest {

    private final ProductService productService = new ProductService();
    private final BrandService brandService = new BrandService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Test(description = "TC_API_01: Verify GET /productsList returns 200 and matches JSON schema")
    public void testGetAllProductsAndSchema() {
        Response response = productService.getAllProducts();
        Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/products-list-schema.json"));
    }

    @Test(description = "TC_API_02: Verify POST /searchProduct with valid term returns matching products")
    public void testSearchProductValid() {
        Response response = productService.searchProduct("top");
        Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
        Assert.assertFalse(response.jsonPath().getList("products").isEmpty(), "Products list should not be empty");
    }

    @Test(description = "TC_API_03: Verify POST /searchProduct without param returns 400 (Negative)")
    public void testSearchProductWithoutParam() throws Exception {
        Response response = productService.searchProductWithoutParam();
        ApiResponse apiRes = mapper.readValue(response.asString(), ApiResponse.class);
        Assert.assertEquals(apiRes.getResponseCode(), 400);
        Assert.assertTrue(apiRes.getMessage().contains("search_product parameter is missing"));
    }

    @Test(description = "TC_API_04: Verify GET /brandsList returns 200 and non-empty brands")
    public void testGetAllBrands() {
        Response response = brandService.getAllBrands();
        Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
        Assert.assertFalse(response.jsonPath().getList("brands").isEmpty(), "Brands list should not be empty");
    }
}