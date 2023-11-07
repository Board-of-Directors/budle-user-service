package ru.nsu.fit.directors.userservice.dto.response;

public record ActionDto(
    String actionName,
    Integer nextStatus
) {
}
