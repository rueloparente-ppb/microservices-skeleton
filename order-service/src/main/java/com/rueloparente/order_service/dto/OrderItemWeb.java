package com.rueloparente.order_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record OrderItemWeb(
        @NotBlank(message = "Product code cannot be blank") String productCode,
        @NotEmpty(message = "Product name cannot be empty") String productName,
        @NotNull(message = "Product price cannot be null") @Min(value = 0, message = "Product price cannot be " + "negative")
                BigDecimal productPrice,
        @NotNull @Min(value = 1, message = "Quantity must be greater than 0") Integer quantity) {}
