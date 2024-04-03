package ru.nsu.fit.directors.userservice.dto.request;

public record ChatMessage(
    Long userId,
    String message,
    Long orderId
) {
}
