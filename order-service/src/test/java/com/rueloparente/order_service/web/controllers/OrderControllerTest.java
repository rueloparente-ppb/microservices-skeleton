package com.rueloparente.order_service.web.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import com.rueloparente.order_service.AbstractIT;
import com.rueloparente.order_service.testdata.TestDataFactory;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class OrderControllerTest extends AbstractIT {

    @Nested
    class CreateOrderTests {
        @Test
        void shouldCreateOrderSucessfully() {
            var payload =
                    """
                            {
                "customer": {
                  "name": "rueloparente",
                  "email": "johndoe@example.com",
                  "phone": "+123456789"
                },
                "deliveryAddress": {
                  "addressLine1": "123 Main Street",
                  "addressLine2": "Apt 4B",
                  "city": "New York",
                  "state": "NY",
                  "zipCode": "10001",
                  "country": "USA"
                },
                "items": [
                  {
                    "productCode": "PROD123",
                    "productName": "Wireless Mouse",
                    "productPrice": 25.99,
                    "quantity": 2
                  },
                  {
                    "productCode": "PROD456",
                    "productName": "Mechanical Keyboard",
                    "productPrice": 89.99,
                    "quantity": 1
                  }
                ]
              }
          """;
            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("orderNumber", notNullValue());
        }

        @Test
        void shouldReturnBadRequestWhenMandatoryDataIsMissing() {
            var payload = TestDataFactory.createOrderRequestWithInvalidCustomer();
            given().contentType(ContentType.JSON)
                    .header("Authorization", "Bearer ")
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}
