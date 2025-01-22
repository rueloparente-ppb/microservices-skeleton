package com.rueloparente.menu_service.domain;

import com.rueloparente.menu_service.dto.ProductWeb;

class ProductMapper {

    public static ProductWeb toProductWeb(Product product) {
        return new ProductWeb(
                product.getCode(),
                product.getCategory(),
                product.getName(),
                product.getDescription(),
                product.getImageUrl(),
                product.getPrice());
    }
}
