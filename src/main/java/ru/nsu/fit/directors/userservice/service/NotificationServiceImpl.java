package ru.nsu.fit.directors.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final SecurityService securityService;

    @Override
    public void handleOrderNotification(OrderNotificationEvent event) {
        User user = userRepository.findById(event.userId()).orElseThrow();
        notificationRepository.save(new Notification().setUser(user).setMessage(event.message()).setWasReceived(false));
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
}
