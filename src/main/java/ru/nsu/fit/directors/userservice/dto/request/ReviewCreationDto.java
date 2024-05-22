package ru.nsu.fit.directors.userservice.dto.request;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;

public record ReviewCreationDto(
    @NotNull(message = "Имя пользователя не может быть не задано.")
    String username,
    @NotNull(message = "Значение заведения не может быть не указано.")
    Long establishmentId,
    @NotNull(message = "Текст не может быть не задан.")
    String text,
    @NotNull(message = "Оценка не может быть не задана.")
    Integer score
) {
    @Nonnull
    public ReviewCreationDto withUsername(String newUsername) {
        return new ReviewCreationDto(newUsername, establishmentId, text, score);
    }
}
