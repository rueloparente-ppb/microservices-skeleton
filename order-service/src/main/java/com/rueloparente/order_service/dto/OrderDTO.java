package com.rueloparente.order_service.dto;

import com.rueloparente.order_service.value_objects.Address;
import com.rueloparente.order_service.value_objects.Customer;
import com.rueloparente.order_service.value_objects.OrderStatus;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderDTO(
        String orderNumber,
        String user,
        Set<OrderItemWeb> items,
        Customer customer,
        Address deliveryAddress,
        OrderStatus status,
        String comments,
        LocalDateTime createdAt) {}
