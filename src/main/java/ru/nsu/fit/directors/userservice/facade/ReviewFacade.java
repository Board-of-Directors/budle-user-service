package ru.nsu.fit.directors.userservice.facade;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import ru.nsu.fit.directors.userservice.dto.request.ReviewCreationDto;
import ru.nsu.fit.directors.userservice.dto.response.ResponseReviewDto;

@ParametersAreNonnullByDefault
public interface ReviewFacade {
    /**
     * Создать отзыв на заведение.
     *
     * @param reviewCreationDto данные об отзыве
     */
    void createReview(ReviewCreationDto reviewCreationDto);

    /**
     * Получить отзывы активного пользователя.
     *
     * @return список отзывов активного пользователя
     */
    @Nonnull
    List<ResponseReviewDto> getReviews();
}
