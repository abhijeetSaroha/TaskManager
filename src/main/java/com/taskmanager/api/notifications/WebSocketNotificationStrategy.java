package com.taskmanager.api.notifications;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WebSocketNotificationStrategy implements NotificationStrategy {
    @Override
    public void sendNotification(String recipient, String message) {
        // In a real app, SimpMessagingTemplate logic goes here
        log.info("Opening WebSocket connection...");
        log.info("Pushing WEBSOCKET alert to [{}]: {}", recipient, message);
    }

    @Override
    public String getStrategyName() {
        return "WEBSOCKET";
    }
}