package pl.dovskyy.spring.currencyexchangenbp.service;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;


class ExternalApiTest {

    /**
     * Test if NBP API is available and returns 200 status code
     */

    @Test
    public void testApiStatus() {
        RestAssured.baseURI = "http://api.nbp.pl";

        given()
                .when()
                .get("/api/exchangerates/tables/a/")
                .then()
                .statusCode(200);
    }
}
