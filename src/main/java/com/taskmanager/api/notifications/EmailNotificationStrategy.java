package com.taskmanager.api.notifications;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailNotificationStrategy implements NotificationStrategy {
    @Override
    public void sendNotification(String recipient, String message) {
        // In a real app, JavaMailSender logic goes here
        log.info("Preparing EMAIL payload...");
        log.info("Sending EMAIL to [{}]: {}", recipient, message);
    }

    @Override
    public String getStrategyName() {
        return "EMAIL";
    }
}