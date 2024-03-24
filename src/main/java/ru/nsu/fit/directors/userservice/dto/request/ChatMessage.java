package ru.nsu.fit.directors.userservice.dto.request;

public record ChatMessage(
    String message,
    Long orderId
) {
}
