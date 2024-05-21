package ru.nsu.fit.directors.userservice.service;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import jakarta.servlet.http.HttpServletRequest;
import ru.nsu.fit.directors.userservice.model.User;

@ParametersAreNonnullByDefault
public interface SecurityService {

    /**
     * Получить активного пользователя.
     *
     * @return данные пользователя
     */
    @Nonnull
    User getLoggedInUser();

    /**
     * Выйти из аккаунта.
     *
     * @param httpServletRequest запрос
     */
    void logout(HttpServletRequest httpServletRequest);
}
