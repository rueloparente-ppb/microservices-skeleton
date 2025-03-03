package com.rueloparente.order_service.dto;

import com.rueloparente.order_service.value_objects.OrderStatus;

public record OrderSummary(String orderNumber, OrderStatus status) {}
