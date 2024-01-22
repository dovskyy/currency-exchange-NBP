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

    @Test
    public void testApiStatus2() {
        RestAssured.baseURI = "http://api.nbp.pl";

        given()
                .when()
                .get("/api/exchangerates/tables/b/")
                .then()
                .statusCode(200);
    }

    @Test
    public void testApiStatus3() {
        RestAssured.baseURI = "http://api.nbp.pl";

        given()
                .when()
                .get("/api/exchangerates/tables/c/")
                .then()
                .statusCode(200);
    }

    @Test
    public void testApiStatus4() {
        RestAssured.baseURI = "http://api.nbp.pl";

        given()
                .when()
                .get("/api/exchangerates/tables/a/2021-06-01/2021-06-07/")
                .then()
                .statusCode(200);
    }

    @Test
    public void testApiStatus5() {
        RestAssured.baseURI = "http://api.nbp.pl";

        given()
                .when()
                .get("/api/exchangerates/tables/b/2021-06-01/2021-06-07/")
                .then()
                .statusCode(200);
    }

    @Test
    public void testApiStatus6() {
        RestAssured.baseURI = "http://api.nbp.pl";

        given()
                .when()
                .get("/api/exchangerates/tables/c/2021-06-01/2021-06-07/")
                .then()
                .statusCode(200);
    }

    @Test
    public void testApiStatus7() {
        RestAssured.baseURI = "http://api.nbp.pl";

        given()
                .when()
                .get("/api/exchangerates/rates/a/eur/2021-06-01/2021-06-07/")
                .then()
                .statusCode(200);
    }
}
