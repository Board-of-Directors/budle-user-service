package ru.nsu.fit.directors.userservice.event;

public record OrderNotificationEvent(String message, Long userId) {
}
