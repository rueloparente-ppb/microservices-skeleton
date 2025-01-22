package com.rueloparente.menu_service.dto;

import java.math.BigDecimal;

public record ProductWeb(
        String code, String category, String name, String description, String imageUrl, BigDecimal price) {}
