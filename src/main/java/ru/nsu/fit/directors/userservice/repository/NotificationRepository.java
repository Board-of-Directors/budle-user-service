package ru.nsu.fit.directors.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.directors.userservice.model.Notification;
import ru.nsu.fit.directors.userservice.model.User;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserAndWasReceived(User loggedInUser, boolean wasReceived);
}
