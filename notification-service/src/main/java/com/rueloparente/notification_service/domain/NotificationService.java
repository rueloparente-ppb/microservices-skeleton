package com.rueloparente.notification_service.domain;

import com.rueloparente.notification_service.ApplicationProperties;
import com.rueloparente.notification_service.dto.OrderCancelledEvent;
import com.rueloparente.notification_service.dto.OrderCreatedEvent;
import com.rueloparente.notification_service.dto.OrderDeliveredEvent;
import com.rueloparente.notification_service.dto.OrderErrorEvent;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


//TODO: There is currently the risk of duplicated e-mail, we could implement a database where we would store the sent
// e-mails.
@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final JavaMailSender emailSender;
    private final ApplicationProperties properties;

    public NotificationService(JavaMailSender emailSender, ApplicationProperties properties) {
        this.emailSender = emailSender;
        this.properties = properties;
    }

    public void sendOrderCreatedNotification(OrderCreatedEvent event) {
        String message =
                """
                ===================================================
                Order Created Notification
                ----------------------------------------------------
                Dear %s,
                Your order with orderNumber: %s has been created successfully.

                Thanks,
                Restaurant team
                ===================================================
                """
                        .formatted(event.customer().name(), event.orderNumber());
        log.info("\n{}", message);
        sendEmail(event.customer().email(), "Order Created Notification", message);
    }

    public void sendOrderDeliveredNotification(OrderDeliveredEvent event) {
        String message =
                """
                ===================================================
                Order Delivered Notification
                ----------------------------------------------------
                Dear %s,
                Your order with orderNumber: %s has been delivered successfully.

                Thanks,
                Restaurant team
                ===================================================
                """
                        .formatted(event.customer().name(), event.orderNumber());
        log.info("\n{}", message);
        sendEmail(event.customer().email(), "Order Delivered Notification", message);
    }

    public void sendOrderCancelledNotification(OrderCancelledEvent event) {
        String message =
                """
                ===================================================
                Order Cancelled Notification
                ----------------------------------------------------
                Dear %s,
                Your order with orderNumber: %s has been cancelled.
                Reason: %s

                Thanks,
                Restaurant team
                ===================================================
                """
                        .formatted(event.customer().name(), event.orderNumber(), event.reason());
        log.info("\n{}", message);
        sendEmail(event.customer().email(), "Order Cancelled Notification", message);
    }

    public void sendOrderErrorEventNotification(OrderErrorEvent event) {
        String message =
                """
                ===================================================
                Order Processing Failure Notification
                ----------------------------------------------------
                Hi %s,
                The order processing failed for orderNumber: %s.
                Reason: %s

                Thanks,
                Restaurant team
                ===================================================
                """
                        .formatted(properties.supportEmail(), event.orderNumber(), event.reason());
        log.info("\n{}", message);
        sendEmail(properties.supportEmail(), "Order Processing Failure Notification", message);
    }

    private void sendEmail(String recipient, String subject, String content) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(properties.supportEmail());
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(content);
            emailSender.send(mimeMessage);
            log.info("Email sent to: {}", recipient);
        } catch (Exception e) {
            throw new RuntimeException("Error while sending email", e);
        }
    }
}
