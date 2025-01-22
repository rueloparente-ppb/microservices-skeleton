package com.rueloparente.menu_service.domain.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }

    public static ProductNotFoundException forCode(String message) {
        return new ProductNotFoundException("Product with" + message);
    }
}
