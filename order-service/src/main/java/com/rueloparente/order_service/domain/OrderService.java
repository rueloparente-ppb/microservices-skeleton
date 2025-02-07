package com.rueloparente.order_service.domain;

import com.rueloparente.order_service.dto.CreateOrderRequest;
import com.rueloparente.order_service.dto.CreateOrderResponse;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderService {
    public static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public CreateOrderResponse createOrder(CreateOrderRequest request, String userName) {
        log.info("Creating order for user: {}", userName);
        Order order = OrderMapper.convertToDomain(request);
        order.setUserName(userName);

        Order savedOrder = orderRepository.save(order);
        log.info("Order created with order number: {}", savedOrder.getOrderNumber());
        return new CreateOrderResponse(savedOrder.getOrderNumber());
    }
}
