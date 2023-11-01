package ru.nsu.fit.directors.userservice.dto.response;

import lombok.Data;

import java.util.Map;

@Data
public class VkNotificationResponse {
    private Map<String, Object> response;
    private Map<String, Object> error;
}
