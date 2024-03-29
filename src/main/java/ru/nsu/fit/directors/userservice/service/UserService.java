package ru.nsu.fit.directors.userservice.service;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import ru.nsu.fit.directors.userservice.model.User;

@ParametersAreNonnullByDefault
public interface UserService {
    /**
     * Сохранять пользователя в базе данных.
     *
     * @param user информация о новом пользователе
     */
    void save(User user);

    /**
     * Получить активного пользователя.
     *
     * @return данные об активном пользователе
     */
    @Nonnull
    User getLoggedInUser();
}
