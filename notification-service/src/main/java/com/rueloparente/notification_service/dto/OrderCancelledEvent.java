package com.rueloparente.notification_service.dto;

import com.rueloparente.notification_service.value_objects.Address;
import com.rueloparente.notification_service.value_objects.Customer;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderCancelledEvent(
        String eventId,
        String orderNumber,
        Set<OrderItemWeb> items,
        Customer customer,
        Address deliveryAddress,
        String reason,
        LocalDateTime createdAt) {}
