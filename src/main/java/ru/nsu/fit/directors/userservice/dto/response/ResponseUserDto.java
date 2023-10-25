package ru.nsu.fit.directors.userservice.dto.response;

import lombok.Builder;

@Builder
public record ResponseUserDto(
    Long id,
    String username,
    String phoneNumber
) {
}
