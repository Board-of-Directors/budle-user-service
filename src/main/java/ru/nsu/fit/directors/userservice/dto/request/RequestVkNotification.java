package ru.nsu.fit.directors.userservice.dto.request;

import java.util.List;

public record RequestVkNotification(
    String message,
    String access_token,
    List<Long> userIds
) {
}
