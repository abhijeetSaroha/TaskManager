package com.taskmanager.api.notifications;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final Map<String, NotificationStrategy> strategies;

    // Spring automatically injects all beans implementing NotificationStrategy
    public NotificationService(List<NotificationStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(NotificationStrategy::getStrategyName, strategy -> strategy));
    }

    public void notify(String type, String recipient, String message) {
        NotificationStrategy strategy = strategies.get(type.toUpperCase());
        if (strategy != null) {
            strategy.sendNotification(recipient, message);
        } else {
            throw new IllegalArgumentException("Invalid notification strategy: " + type);
        }
    }
}