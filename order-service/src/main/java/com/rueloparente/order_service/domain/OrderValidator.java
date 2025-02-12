package com.rueloparente.order_service.domain;

import com.rueloparente.order_service.clients.ProductServiceClient;
import com.rueloparente.order_service.domain.exceptions.InvalidOrderException;
import com.rueloparente.order_service.dto.CreateOrderRequest;
import com.rueloparente.order_service.dto.OrderItemWeb;
import com.rueloparente.order_service.dto.ProductWeb;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator {
    private static final Logger log = LoggerFactory.getLogger(OrderValidator.class);
    private final ProductServiceClient productServiceClient;

    OrderValidator(ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    void validate(CreateOrderRequest request) {
        Set<OrderItemWeb> orderItems = request.items();
        for (OrderItemWeb orderItem : orderItems) {
            ProductWeb product = productServiceClient
                    .getProductByCode(orderItem.productCode())
                    .orElseThrow(() -> new InvalidOrderException("Invalid product code: " + orderItem.productCode()));
            if (product.price().compareTo(orderItem.productPrice()) != 0) {
                log.error("Product price mismatch for product code: {}", orderItem.productCode());
                throw new InvalidOrderException("Product price mismatch");
            }
        }
    }
}
