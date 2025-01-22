package com.rueloparente.menu_service.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_generator")
    @SequenceGenerator(name = "product_id_generator", sequenceName = "product_id_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Product code is required")
    private String code;

    @Column(nullable = false)
    @NotEmpty(message = "Category is required")
    private String category;

    @Column(nullable = false)
    @NotEmpty(message = "Name is required")
    private String name;

    private String description;

    private String imageUrl;

    @NotNull(message = "Price is required") @DecimalMin("0,1")
    @Column(nullable = false)
    private BigDecimal price;

    Product(Product.Builder builder) {}

    public Product() {}

    public Long getId() {
        return id;
    }

    public @NotEmpty(message = "Product code is required") String getCode() {
        return code;
    }

    public @NotEmpty(message = "Category is required") String getCategory() {
        return category;
    }

    public @NotEmpty(message = "Name is required") String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public @NotNull(message = "Price is required") @DecimalMin("0,1") BigDecimal getPrice() {
        return price;
    }

    public static Product.Builder builder() {
        return new Product.Builder();
    }

    static class Builder {
        private Long id;
        private String code;
        private String category;
        private String name;
        private String description;
        private String imageUrl;
        private BigDecimal price;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
