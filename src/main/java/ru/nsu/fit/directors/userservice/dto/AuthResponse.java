package ru.nsu.fit.directors.userservice.dto;

import lombok.Builder;

@Builder
public record AuthResponse(
    String accessToken,
    String refreshToken
) {
}
