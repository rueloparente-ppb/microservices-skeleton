package com.rueloparente.order_service.domain;

import com.rueloparente.order_service.dto.CreateOrderRequest;
import com.rueloparente.order_service.dto.CreateOrderResponse;
import com.rueloparente.order_service.dto.OrderCreatedEvent;
import com.rueloparente.order_service.dto.OrderDTO;
import com.rueloparente.order_service.dto.OrderSummary;
import com.rueloparente.order_service.value_objects.OrderStatus;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {
    public static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private static final List<String> DELIVERY_ALLOWED_COUNTRIES = List.of("PORTUGAL", "RESTAURANT");

    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final OrderEventService orderEventService;

    OrderService(OrderRepository orderRepository, OrderValidator orderValidator, OrderEventService orderEventService) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.orderEventService = orderEventService;
    }

    public CreateOrderResponse createOrder(CreateOrderRequest request, String userName) {
        orderValidator.validate(request);
        log.info("Creating order for user: {}", userName);
        Order order = OrderMapper.convertToDomain(request);
        order.setUserName(userName);
        Order savedOrder = orderRepository.save(order);
        OrderCreatedEvent orderCreatedEvent = OrderEventMapper.buildOrderCreatedEvent(savedOrder);
        orderEventService.save(orderCreatedEvent);
        log.info("Order created with order number: {}", savedOrder.getOrderNumber());
        return new CreateOrderResponse(savedOrder.getOrderNumber());
    }

    public void processNewOrders() {
        log.info("Processing new orders");
        orderRepository.findByStatus(OrderStatus.NEW).forEach(this::process);
    }

    private void process(Order order) {
        try {
            if (canBeDelivered(order)) {
                log.info("Order can be delivered: {}", order.getOrderNumber());
                orderRepository.updateOrderStatus(order.getOrderNumber(), OrderStatus.DELIVERED);
                orderEventService.save(OrderEventMapper.buildOrderDeliveredEvent(order));
            } else {
                log.info("Order cannot be delivered: {}", order.getOrderNumber());
                orderRepository.updateOrderStatus(order.getOrderNumber(), OrderStatus.CANCELLED);
                orderEventService.save(OrderEventMapper.buildOrderCancelledEvent(order, "Cannot be delivered"));
            }
        } catch (Exception e) {
            log.error("Error processing order: {}", order.getOrderNumber(), e);
            orderRepository.updateOrderStatus(order.getOrderNumber(), OrderStatus.ERROR);
            orderEventService.save(OrderEventMapper.buildOrderErrorEvent(order, e.getMessage()));
        }
    }

    private boolean canBeDelivered(Order order) {
        return DELIVERY_ALLOWED_COUNTRIES.contains(order.getDeliveryAddress().country());
    }

    public List<OrderSummary> getOrders(String userName) {
        return orderRepository.findByUserName(userName);
    }

    public Optional<OrderDTO> findUserOrder(String orderNumber, String userName) {
        return orderRepository
                .findByUserNameAndOrderNumber(userName, orderNumber)
                .map(OrderMapper::convertToDTO);
    }
}
