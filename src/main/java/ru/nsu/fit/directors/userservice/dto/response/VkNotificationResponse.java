package ru.nsu.fit.directors.userservice.dto.response;

import lombok.Data;

@Data
public class VkNotificationResponse {
    private Long userId;
    private boolean status;
}
