package com.rueloparente.order_service.domain;

import com.rueloparente.order_service.dto.CreateOrderRequest;
import com.rueloparente.order_service.dto.OrderItemWeb;
import com.rueloparente.order_service.value_objects.OrderStatus;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

class OrderMapper {

    static Order convertToDomain(CreateOrderRequest request) {
        Order newOrder = new Order();
        newOrder.setOrderNumber(UUID.randomUUID().toString());
        newOrder.setStatus(OrderStatus.NEW);
        newOrder.setCustomer(request.customer());
        newOrder.setDeliveryAddress(request.deliveryAddress());
        Set<OrderItem> orderItems = new HashSet<>();
        for (OrderItemWeb orderItemWeb : request.items()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setCode(orderItemWeb.productCode());
            orderItem.setName(orderItemWeb.productName());
            orderItem.setPrice(orderItemWeb.productPrice());
            orderItem.setQuantity(orderItemWeb.quantity());
            orderItem.setOrder(newOrder);
            orderItems.add(orderItem);
        }
        newOrder.setItems(orderItems);
        return newOrder;
    }
}
