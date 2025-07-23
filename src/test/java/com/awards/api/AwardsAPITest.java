package com.awards.api;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;

@QuarkusTest
class AwardsAPITest {
    @Test
    void shouldDoAnUniqueResultWithTheAward() {
        RestAssured.given()
                .when().get("/producers/intervals")
                .then()
                .statusCode(200)
                .body(is("{\"min\":[],\"max\":[]}"));
    }

}