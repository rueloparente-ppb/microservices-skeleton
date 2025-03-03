package com.rueloparente.order_service.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rueloparente.order_service.dto.OrderCancelledEvent;
import com.rueloparente.order_service.dto.OrderCreatedEvent;
import com.rueloparente.order_service.dto.OrderDeliveredEvent;
import com.rueloparente.order_service.dto.OrderErrorEvent;
import com.rueloparente.order_service.value_objects.OrderEventType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderEventService {
    private static final Logger log = LoggerFactory.getLogger(OrderEventService.class);
    private final OrderEventRepository orderEventRepository;
    private final OrderEventPublisher orderEventPublisher;
    private final ObjectMapper objectMapper;

    OrderEventService(
            OrderEventRepository orderEventRepository,
            OrderEventPublisher orderEventPublisher,
            ObjectMapper objectMapper) {
        this.orderEventRepository = orderEventRepository;
        this.orderEventPublisher = orderEventPublisher;
        this.objectMapper = objectMapper;
    }

    void save(OrderCreatedEvent orderCreatedEvent) {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setEventId(orderCreatedEvent.eventId());
        orderEvent.setEventType(OrderEventType.ORDER_CREATED);
        orderEvent.setOrderNumber(orderCreatedEvent.orderNumber());
        orderEvent.setCreatedAt(orderCreatedEvent.createdAt());
        orderEvent.setPayload(toJsonPayload(orderCreatedEvent));
        this.orderEventRepository.save(orderEvent);
    }

    void save(OrderCancelledEvent orderCancelledEvent) {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setEventId(orderCancelledEvent.eventId());
        orderEvent.setEventType(OrderEventType.ORDER_CANCELLED);
        orderEvent.setOrderNumber(orderCancelledEvent.orderNumber());
        orderEvent.setCreatedAt(orderCancelledEvent.createdAt());
        orderEvent.setPayload(toJsonPayload(orderCancelledEvent));
        this.orderEventRepository.save(orderEvent);
    }

    void save(OrderErrorEvent orderErrorEvent) {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setEventId(orderErrorEvent.eventId());
        orderEvent.setEventType(OrderEventType.ORDER_PROCESSING_FAILED);
        orderEvent.setOrderNumber(orderErrorEvent.orderNumber());
        orderEvent.setCreatedAt(orderErrorEvent.createdAt());
        orderEvent.setPayload(toJsonPayload(orderErrorEvent));
        this.orderEventRepository.save(orderEvent);
    }

    void save(OrderDeliveredEvent orderDeliveredEvent) {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setEventId(orderDeliveredEvent.eventId());
        orderEvent.setEventType(OrderEventType.ORDER_DELIVERED);
        orderEvent.setOrderNumber(orderDeliveredEvent.orderNumber());
        orderEvent.setCreatedAt(orderDeliveredEvent.createdAt());
        orderEvent.setPayload(toJsonPayload(orderDeliveredEvent));
        this.orderEventRepository.save(orderEvent);
    }

    public void publishOrdersEvents() {
        Sort sort = Sort.by("createdAt").ascending();
        List<OrderEvent> events = orderEventRepository.findAll(sort);
        log.info("Publishing {} events", events.size());
        for (OrderEvent orderEvent : events) {
            publishOrderEvent(orderEvent);
            orderEventRepository.delete(orderEvent);
        }
    }

    private void publishOrderEvent(OrderEvent orderEvent) {
        OrderEventType eventType = orderEvent.getEventType();
        switch (eventType) {
            case ORDER_CREATED:
                OrderCreatedEvent orderCreatedEvent = fromJsonPayload(orderEvent.getPayload(), OrderCreatedEvent.class);
                orderEventPublisher.publish(orderCreatedEvent);
                break;
            case ORDER_CANCELLED:
                OrderCancelledEvent orderCancelledEvent =
                        fromJsonPayload(orderEvent.getPayload(), OrderCancelledEvent.class);
                orderEventPublisher.publish(orderCancelledEvent);
                break;
            case ORDER_DELIVERED:
                OrderDeliveredEvent orderDeliveredEvent =
                        fromJsonPayload(orderEvent.getPayload(), OrderDeliveredEvent.class);
                orderEventPublisher.publish(orderDeliveredEvent);
                break;
            case ORDER_PROCESSING_FAILED:
                OrderErrorEvent orderErrorEvent = fromJsonPayload(orderEvent.getPayload(), OrderErrorEvent.class);
                orderEventPublisher.publish(orderErrorEvent);
                break;
            default:
                log.warn("Unsupported event type: {}", eventType);
        }
    }

    private <T> T fromJsonPayload(String payload, Class<T> clazz) {
        try {
            return objectMapper.readValue(payload, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Error converting order event payload to object", e);
        }
    }

    private String toJsonPayload(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Error converting order event request to json", e);
        }
    }
}
