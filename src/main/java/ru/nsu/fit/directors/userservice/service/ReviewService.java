package ru.nsu.fit.directors.userservice.service;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import ru.nsu.fit.directors.userservice.model.User;
import ru.nsu.fit.directors.userservice.model.UserReview;

@ParametersAreNonnullByDefault
public interface ReviewService {
    /**
     * Сохранить отзыв.
     *
     * @param userReview пользовательский отзыв
     */
    void save(UserReview userReview);

    /**
     * Получить пользовательские отзывы.
     *
     * @param user пользователь
     * @return список отзывов
     */
    @Nonnull
    List<UserReview> getUserReviews(User user);
}
