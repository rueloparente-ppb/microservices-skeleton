package com.rueloparente.menu_service;

import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties
public record ApplicationProperties(@DefaultValue("10") @Min(1) int pagSize) {}
