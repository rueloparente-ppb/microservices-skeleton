package com.rueloparente.order_service.jobs;

import com.rueloparente.order_service.domain.OrderService;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderProcessingJob {
    private static final Logger log = LoggerFactory.getLogger(OrderProcessingJob.class);
    private final OrderService orderService;

    OrderProcessingJob(OrderService orderService) {
        this.orderService = orderService;
    }

    @Scheduled(cron = "*/10 * * * * *")
    @SchedulerLock(name = "processNewOrders")
    public void processNewOrders() {
        LockAssert.assertLocked();
        log.info("Processing new orders at {}", System.currentTimeMillis());
        orderService.processNewOrders();
    }
}
