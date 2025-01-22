package com.rueloparente.menu_service.web.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.rueloparente.menu_service.AbstractIT;
import com.rueloparente.menu_service.dto.ProductWeb;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql("/test-data.sql")
class ProductControllerTest extends AbstractIT {

    @Test
    void shouldReturnProducts() {
        given().contentType(ContentType.JSON)
                .when()
                .get("/api/products")
                .then()
                .statusCode(200)
                .body("data", hasSize(10))
                .body("totalElements", is(15))
                .body("pageNumber", is(1))
                .body("totalPages", is(2))
                .body("isFirst", is(true))
                .body("isLast", is(false))
                .body("hasNext", is(true))
                .body("hasPrevious", is(false));
    }

    @Test
    void shouldReturnProductWithCode() {
        ProductWeb result = given().contentType(ContentType.JSON)
                .when()
                .get("/api/products/FRB")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(ProductWeb.class);

        assertEquals(result.code(), "FRB");
        assertEquals(result.name(), "Francesinha");
        assertEquals(
                result.description(),
                "A towering sandwich filled with cured meats, smothered in melted cheese, and drenched in a spicy beer-based sauce.");
        assertEquals(result.price().toString(), "18.9");
    }
}
