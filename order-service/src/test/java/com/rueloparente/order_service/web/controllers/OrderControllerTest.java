package com.rueloparente.order_service.web.controllers;

import static com.rueloparente.order_service.web.controllers.WireMockDataFactory.mockGetProductByCode;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;

import com.rueloparente.order_service.AbstractIT;
import com.rueloparente.order_service.dto.OrderSummary;
import com.rueloparente.order_service.testdata.TestDataFactory;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "/test-orders.sql")
class OrderControllerTest extends AbstractIT {

    @Nested
    class CreateOrderTests {
        @Test
        void shouldCreateOrderSucessfully() {
            mockGetProductByCode("PROD123", "Wireless Mouse", BigDecimal.valueOf(25.99));
            mockGetProductByCode("PROD456", "Mechanical Keyboard", BigDecimal.valueOf(89.99));
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

    @Nested
    class GetOrderTests {
        @Test
        void shouldGetOrdersSuccessfully() {
            List<OrderSummary> orderSummaries = given().when()
                    .get("/api/orders")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .extract()
                    .body()
                    .as(new TypeRef<>() {});

            assertTrue(orderSummaries.size() == 2);
        }

        @Nested
        class GetOrderByOrderNumberTests {
            String orderNumber = "order-123";

            @Test
            void shouldGetOrderSuccessfully() {
                given().when()
                        .get("/api/orders/{orderNumber}", orderNumber)
                        .then()
                        .statusCode(200)
                        .body("orderNumber", is(orderNumber))
                        .body("items.size()", is(2));
            }
        }
    }
}
