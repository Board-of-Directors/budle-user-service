package ru.nsu.fit.directors.userservice.dto;

import java.time.LocalDateTime;

public record MessageDto(String message, LocalDateTime timestamp, String senderType) {
}
