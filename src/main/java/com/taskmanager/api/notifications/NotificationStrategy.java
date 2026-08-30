package com.taskmanager.api.notifications;

public interface NotificationStrategy {
    void sendNotification(String recipient, String message);
    String getStrategyName();
}