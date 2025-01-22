package com.rueloparente.menu_service.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest(
        properties = {"spring.test.database.replace=node", "spring.datasource.url=jdbc:tc:postgresql:16-alpine:///db"})
@Sql("/test-data.sql")
class ProductRepositorySpringTest {
    @Autowired
    private ProductRepositorySpring productRepository;

    @Test
    void shouldReturnAllProductsOfGivenCategory() {
        // Arrange
        String category = "Main Course";
        Pageable pageable = Pageable.unpaged();
        // Act
        Page<Product> products = productRepository.findByCategory(category, pageable);
        // Assert
        assertEquals(7, products.getTotalElements());
    }

    @Test
    void shouldReturnProductByCode() {
        Product expectedProduct = productRepository.findByCode("FRB").orElseThrow();
        assertEquals(expectedProduct.getCode(), "FRB");
        assertEquals(expectedProduct.getName(), "Francesinha");
        assertEquals(
                expectedProduct.getDescription(),
                "A towering sandwich filled with cured meats, smothered in melted cheese, and drenched in a spicy beer-based sauce.");
        assertEquals(expectedProduct.getCategory(), "Main Course");
        assertEquals(expectedProduct.getPrice().toString(), "18.9");
    }

    @Test
    void shouldReturnEmptyWhenProductNotFound() {
        assertThat(productRepository.findByCode("NONEXISTE").isEmpty());
    }
}
