package ru.nsu.fit.directors.userservice.dto;

import ru.nsu.fit.directors.userservice.dto.response.ResponseOrderDto;

public record NotificationDto(String message, ResponseOrderDto order) {
}
