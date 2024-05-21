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
     * Получить пользователя по номеру телефона.
     *
     * @param phoneNumber номер телефона
     * @return пользователя
     */
    @Nonnull
    User getByPhoneNumber(String phoneNumber);

    /**
     * Получить пользователя по идентификатору.
     * @param userId идентификатор пользователя
     * @return пользователь
     */
    @Nonnull
    User getUserById(Long userId);
}
