package ru.nsu.fit.directors.userservice.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.nsu.fit.directors.userservice.model.Notification;
import ru.nsu.fit.directors.userservice.model.User;

public interface ReactiveNotificationRepository extends ReactiveCrudRepository<Notification, Long> {
    Flux<Notification> findByUserAndWasReceived(User loggedInUser, boolean wasReceived);
}
