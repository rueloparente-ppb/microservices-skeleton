package com.rueloparente.order_service.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface OrderEventRepository extends JpaRepository<OrderEvent, Long> {}
