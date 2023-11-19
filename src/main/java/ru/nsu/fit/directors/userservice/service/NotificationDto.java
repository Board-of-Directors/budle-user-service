package ru.nsu.fit.directors.userservice.service;

import ru.nsu.fit.directors.userservice.dto.response.ResponseOrderDto;

public record NotificationDto(String message, ResponseOrderDto order) {
}
