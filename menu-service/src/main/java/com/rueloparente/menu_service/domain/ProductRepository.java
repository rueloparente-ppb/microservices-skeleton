package com.rueloparente.menu_service.domain;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository {
    Page<Product> findAll(Pageable pageable);

    Page<Product> findByCategory(String category, Pageable pageable);

    Optional<Product> findByCode(String code);
}
