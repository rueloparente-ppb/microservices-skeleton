package com.rueloparente.order_service.domain;

import com.rueloparente.order_service.dto.OrderCancelledEvent;
import com.rueloparente.order_service.dto.OrderCreatedEvent;
import com.rueloparente.order_service.dto.OrderDeliveredEvent;
import com.rueloparente.order_service.dto.OrderErrorEvent;
import com.rueloparente.order_service.dto.OrderItemWeb;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class OrderEventMapper {

    static OrderCancelledEvent buildOrderCancelledEvent(Order order, String reason) {
        return new OrderCancelledEvent(
                UUID.randomUUID().toString(),
                order.getOrderNumber(),
                convertToOrderItemWeb(order.getItems()),
                order.getCustomer(),
                order.getDeliveryAddress(),
                reason,
                LocalDateTime.now());
    }

    static OrderErrorEvent buildOrderErrorEvent(Order order, String reason) {
        return new OrderErrorEvent(
                UUID.randomUUID().toString(),
                order.getOrderNumber(),
                convertToOrderItemWeb(order.getItems()),
                order.getCustomer(),
                order.getDeliveryAddress(),
                reason,
                LocalDateTime.now());
    }

    static OrderDeliveredEvent buildOrderDeliveredEvent(Order order) {
        return new OrderDeliveredEvent(
                UUID.randomUUID().toString(),
                order.getOrderNumber(),
                convertToOrderItemWeb(order.getItems()),
                order.getCustomer(),
                order.getDeliveryAddress(),
                LocalDateTime.now());
    }

    static OrderCreatedEvent buildOrderCreatedEvent(Order order) {
        return new OrderCreatedEvent(
                UUID.randomUUID().toString(),
                order.getOrderNumber(),
                convertToOrderItemWeb(order.getItems()),
                order.getCustomer(),
                order.getDeliveryAddress(),
                LocalDateTime.now());
    }

    private static Set<OrderItemWeb> convertToOrderItemWeb(Set<OrderItem> items) {
        Set<OrderItemWeb> orderItemWebs = new HashSet<>();
        for (OrderItem orderItem : items) {
            orderItemWebs.add(new OrderItemWeb(
                    orderItem.getCode(), orderItem.getName(), orderItem.getPrice(), orderItem.getQuantity()));
        }
        return orderItemWebs;
    }
}
