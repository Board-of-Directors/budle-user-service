package ru.nsu.fit.directors.userservice.event;

public record UserMessageEvent(Long userId, Long orderId, String message) {
}
