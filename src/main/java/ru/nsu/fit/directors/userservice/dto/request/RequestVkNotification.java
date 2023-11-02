package ru.nsu.fit.directors.userservice.dto.request;

public record RequestVkNotification(
    String message,
    String access_token,
    String user_ids
) {
}
