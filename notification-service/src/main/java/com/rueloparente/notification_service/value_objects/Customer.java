package com.rueloparente.notification_service.value_objects;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record Customer(
        @NotEmpty(message = "Name is required") String name,
        @NotEmpty(message = "Email is required") @Email String email,
        @NotEmpty(message = "Phone is required") String phone) {}
