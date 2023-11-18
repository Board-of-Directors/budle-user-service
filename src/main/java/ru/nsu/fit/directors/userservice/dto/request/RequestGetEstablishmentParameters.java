package ru.nsu.fit.directors.userservice.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import org.springframework.lang.Nullable;


@Builder
public record RequestGetEstablishmentParameters(
    String name,
    @Nullable String category,
    @Nullable Boolean hasMap,
    @Nullable Boolean hasCardPayment,
    @Min(value = 5, message = "Не может быть меньше 5 рабочих дней.")
    @Max(value = 7, message = "Не может быть больше 7 рабочих дней.")
    Integer workingDayCount,
    @Min(value = 0, message = "Страница не может быть меньше нулевой.") Integer offset,
    @Min(value = 1, message = "Лимит не может быть меньше единицы.") Integer limit,
    String sortValue
) {
}
