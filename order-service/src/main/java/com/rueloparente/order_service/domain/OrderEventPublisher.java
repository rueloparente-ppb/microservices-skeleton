package com.rueloparente.order_service.domain;

import com.rueloparente.order_service.dto.OrderCancelledEvent;
import com.rueloparente.order_service.dto.OrderCreatedEvent;
import com.rueloparente.order_service.dto.OrderDeliveredEvent;
import com.rueloparente.order_service.dto.OrderErrorEvent;

public interface OrderEventPublisher {

    void publish(OrderCreatedEvent orderCreatedEvent);

    void publish(OrderCancelledEvent orderCancelledEvent);

    void publish(OrderDeliveredEvent orderDeliveredEvent);

    void publish(OrderErrorEvent orderErrorEvent);
}
