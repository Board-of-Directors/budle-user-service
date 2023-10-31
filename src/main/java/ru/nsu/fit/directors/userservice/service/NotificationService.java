package ru.nsu.fit.directors.userservice.service;

import ru.nsu.fit.directors.userservice.event.OrderNotificationEvent;

import java.util.List;

public interface NotificationService {
    void handleOrderNotification(OrderNotificationEvent orderNotificationEvent);

    List<NotificationDto> getNotifications();
}
