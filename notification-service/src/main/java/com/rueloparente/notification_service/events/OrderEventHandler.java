package com.rueloparente.notification_service.events;

import com.rueloparente.notification_service.domain.NotificationService;
import com.rueloparente.notification_service.dto.OrderCancelledEvent;
import com.rueloparente.notification_service.dto.OrderCreatedEvent;
import com.rueloparente.notification_service.dto.OrderDeliveredEvent;
import com.rueloparente.notification_service.dto.OrderErrorEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventHandler {

    private final NotificationService notificationService;

    public OrderEventHandler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "${notification.new-orders-queue}")
    void handleOrderCreatedEvent(OrderCreatedEvent event) {
        notificationService.sendOrderCreatedNotification(event);
    }

    @RabbitListener(queues = "${notification.delivered-orders-queue}")
    void handleOrderDeliveredEvent(OrderDeliveredEvent event) {
        notificationService.sendOrderDeliveredNotification(event);
    }

    @RabbitListener(queues = "${notification.cancelled-orders-queue}")
    void handleOrderCancelledEvent(OrderCancelledEvent event) {
        notificationService.sendOrderCancelledNotification(event);
    }

    @RabbitListener(queues = "${notification.error-orders-queue}")
    void handleOrderErrorEvent(OrderErrorEvent event) {
        notificationService.sendOrderErrorEventNotification(event);
    }

}
