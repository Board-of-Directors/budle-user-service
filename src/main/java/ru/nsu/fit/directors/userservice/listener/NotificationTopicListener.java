package ru.nsu.fit.directors.userservice.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.userservice.event.OrderNotificationEvent;
import ru.nsu.fit.directors.userservice.service.NotificationService;

import javax.annotation.ParametersAreNonnullByDefault;

@Component
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
@KafkaListener(topics = "notificationTopic")
public class NotificationTopicListener {
    private final NotificationService notificationService;

    @KafkaHandler
    public void handleOrderNotification(OrderNotificationEvent orderNotificationEvent){
        notificationService.handleOrderNotification(orderNotificationEvent);

    }
}
