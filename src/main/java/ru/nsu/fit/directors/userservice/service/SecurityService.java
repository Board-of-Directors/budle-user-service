package ru.nsu.fit.directors.userservice.service;

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
    User getLoggedInUser();

    /**
     * Выполнить вход.
     *
     * @param username имя пользователя
     * @param password пароль
     * @param request  запрос
     */
    void autoLogin(String username, String password, HttpServletRequest request);

    /**
     * Выйти из аккаунта.
     *
     * @param httpServletRequest запрос
     */
    void logout(HttpServletRequest httpServletRequest);
}
