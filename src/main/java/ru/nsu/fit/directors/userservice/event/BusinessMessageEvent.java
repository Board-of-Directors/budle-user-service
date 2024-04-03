package ru.nsu.fit.directors.userservice.event;

public record BusinessMessageEvent(Long userId, Long orderId, String message) {
}
