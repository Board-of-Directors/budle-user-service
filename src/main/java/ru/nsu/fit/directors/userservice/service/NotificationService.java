package ru.nsu.fit.directors.userservice.service;

import reactor.core.publisher.Flux;
import ru.nsu.fit.directors.userservice.event.OrderNotificationEvent;

import java.util.List;

public interface NotificationService {
    void handleOrderNotification(OrderNotificationEvent orderNotificationEvent);

    List<NotificationDto> getNotifications();

    Flux<NotificationDto> getFluxNotifications();
}
