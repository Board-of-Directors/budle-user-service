package ru.nsu.fit.directors.userservice.dto.response;

import java.time.LocalDate;

public record ResponseReviewDto(
    String username,
    String text,
    Integer score,
    LocalDate date
) {
}
