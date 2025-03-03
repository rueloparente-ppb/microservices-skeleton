package com.rueloparente.order_service.web.controllers;

import com.rueloparente.order_service.domain.OrderService;
import com.rueloparente.order_service.domain.SecurityService;
import com.rueloparente.order_service.domain.exceptions.OrderNotFoundException;
import com.rueloparente.order_service.dto.CreateOrderRequest;
import com.rueloparente.order_service.dto.CreateOrderResponse;
import com.rueloparente.order_service.dto.OrderDTO;
import com.rueloparente.order_service.dto.OrderSummary;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/orders")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final SecurityService securityService;

    OrderController(OrderService orderService, SecurityService securityService) {
        this.orderService = orderService;
        this.securityService = securityService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
        String userName = securityService.getLoggedInUserName();
        log.info("Creating order for user: {}", userName);
        return orderService.createOrder(request, userName);
    }

    @GetMapping
    List<OrderSummary> getOrders() {
        String userName = securityService.getLoggedInUserName();
        log.info("Getting orders for user: {}", userName);
        return orderService.getOrders(userName);
    }

    @GetMapping(value = "/{orderNumber}")
    OrderDTO getOrder(@PathVariable(value = "orderNumber") String orderNumber) {
        log.info("Fetching order by order number: {}", orderNumber);
        String userName = securityService.getLoggedInUserName();
        return orderService
                .findUserOrder(orderNumber, userName)
                .orElseThrow(() -> new OrderNotFoundException(orderNumber));
    }
}
