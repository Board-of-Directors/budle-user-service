package ru.nsu.fit.directors.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.nsu.fit.directors.userservice.api.VkApi;
import ru.nsu.fit.directors.userservice.dto.request.RequestVkNotification;
import ru.nsu.fit.directors.userservice.event.OrderNotificationEvent;
import ru.nsu.fit.directors.userservice.model.Notification;
import ru.nsu.fit.directors.userservice.model.User;
import ru.nsu.fit.directors.userservice.repository.NotificationRepository;
import ru.nsu.fit.directors.userservice.repository.UserRepository;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final SecurityService securityService;
    private final VkApi vkApi;

    @Value("${vk.api.service-key}")
    private String vkServiceKey;

    @Override
    public void handleOrderNotification(OrderNotificationEvent event) {
        User user = userRepository.findById(event.userId()).orElseThrow();
        try {
            if (user.getVkUserId() != null) {
                vkApi.sendNotification(
                    new RequestVkNotification(event.message(), vkServiceKey, String.valueOf(user.getVkUserId()))
                );
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            saveNotification(event, user);
        }
    }

    private void saveNotification(OrderNotificationEvent event, User user) {
        notificationRepository.save(
            new Notification()
                .setUser(user)
                .setMessage(event.message())
                .setOrderId(event.orderId())
                .setWasReceived(false)
        );
    }

    @Override
    public List<NotificationDto> getNotifications() {
        List<Notification> notifications = notificationRepository.findByUserAndWasReceived(
            securityService.getLoggedInUser(),
            false
        );
        notifications.forEach(notification -> notification.setWasReceived(true));
        notificationRepository.saveAll(notifications);
        return notifications.stream()
            .map(notification -> new NotificationDto(notification.getMessage()))
            .toList();
    }

    @Override
    public Flux<NotificationDto> getFluxNotifications() {
        return Flux.fromIterable(getNotifications());
    }
}
