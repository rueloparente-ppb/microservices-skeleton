package com.rueloparente.order_service.testdata;

import static org.instancio.Select.field;

import com.rueloparente.order_service.dto.CreateOrderRequest;
import com.rueloparente.order_service.dto.OrderItemWeb;
import com.rueloparente.order_service.value_objects.Address;
import com.rueloparente.order_service.value_objects.Customer;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.instancio.Instancio;

public class TestDataFactory {
    static final List<String> VALID_COUNTIES = List.of("Portugal", "USA");
    static final Set<OrderItemWeb> VALID_ORDER_ITEMS =
            Set.of(new OrderItemWeb("BGS", "Product 1", new BigDecimal("25.50"), 1));
    static final Set<OrderItemWeb> INVALID_ORDER_ITEMS =
            Set.of(new OrderItemWeb("ABCD", "Product 1", new BigDecimal("25.50"), 1));

    public static CreateOrderRequest createValidOrderRequest() {
        return Instancio.of(CreateOrderRequest.class)
                .generate(field(Customer::email), gen -> gen.text().pattern("#a#a#a#a#a#a@mail.com"))
                .set(field(CreateOrderRequest::items), VALID_ORDER_ITEMS)
                .generate(field(Address::country), gen -> gen.oneOf(VALID_COUNTIES))
                .create();
    }

    public static CreateOrderRequest createOrderRequestWithInvalidCustomer() {
        return Instancio.of(CreateOrderRequest.class)
                .generate(field(Customer::email), gen -> gen.text().pattern("#c#c#c#c#d#d@mail.com"))
                .set(field(Customer::phone), "")
                .generate(field(Address::country), gen -> gen.oneOf(VALID_COUNTIES))
                .set(field(CreateOrderRequest::items), VALID_ORDER_ITEMS)
                .create();
    }

    public static CreateOrderRequest createOrderRequestWithInvalidDeliveryAddress() {
        return Instancio.of(CreateOrderRequest.class)
                .generate(field(Customer::email), gen -> gen.text().pattern("#c#c#c#c#d#d@mail.com"))
                .set(field(Address::country), "")
                .set(field(CreateOrderRequest::items), VALID_ORDER_ITEMS)
                .create();
    }

    public static CreateOrderRequest createOrderRequestWithNoItems() {
        return Instancio.of(CreateOrderRequest.class)
                .generate(field(Customer::email), gen -> gen.text().pattern("#c#c#c#c#d#d@mail.com"))
                .generate(field(Address::country), gen -> gen.oneOf(VALID_COUNTIES))
                .set(field(CreateOrderRequest::items), Set.of())
                .create();
    }
}
