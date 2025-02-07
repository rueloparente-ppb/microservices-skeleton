package com.rueloparente.order_service.dto;

import com.rueloparente.order_service.value_objects.Address;
import com.rueloparente.order_service.value_objects.Customer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public record CreateOrderRequest(
        @Valid Customer customer,
        @Valid Address deliveryAddress,
        @Valid @NotEmpty(message = "Itens cannot be empty") Set<OrderItemWeb> items) {}
