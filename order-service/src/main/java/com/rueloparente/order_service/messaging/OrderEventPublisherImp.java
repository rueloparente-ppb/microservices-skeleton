package com.rueloparente.order_service.messaging;

import com.rueloparente.order_service.ApplicationProperties;
import com.rueloparente.order_service.domain.OrderEventPublisher;
import com.rueloparente.order_service.dto.OrderCancelledEvent;
import com.rueloparente.order_service.dto.OrderCreatedEvent;
import com.rueloparente.order_service.dto.OrderDeliveredEvent;
import com.rueloparente.order_service.dto.OrderErrorEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisherImp implements OrderEventPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final ApplicationProperties applicationProperties;

    OrderEventPublisherImp(RabbitTemplate rabbitTemplate, ApplicationProperties applicationProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.applicationProperties = applicationProperties;
    }

    public void publish(OrderCreatedEvent orderCreatedEvent) {
        this.send(applicationProperties.newOrdersQueue(), orderCreatedEvent);
    }

    @Override
    public void publish(OrderCancelledEvent orderCancelledEvent) {
        this.send(applicationProperties.cancelledOrdersQueue(), orderCancelledEvent);
    }

    @Override
    public void publish(OrderDeliveredEvent orderDeliveredEvent) {
        this.send(applicationProperties.deliveredOrdersQueue(), orderDeliveredEvent);
    }

    @Override
    public void publish(OrderErrorEvent orderErrorEvent) {
        this.send(applicationProperties.errorOrdersQueue(), orderErrorEvent);
    }

    private void send(String routingKey, Object payload) {
        rabbitTemplate.convertAndSend(applicationProperties.orderEventsExchange(), routingKey, payload);
    }
}
