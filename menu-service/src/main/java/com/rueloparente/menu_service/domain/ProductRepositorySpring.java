package com.rueloparente.menu_service.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ProductRepositorySpring extends ProductRepository, JpaRepository<Product, Long> {}
