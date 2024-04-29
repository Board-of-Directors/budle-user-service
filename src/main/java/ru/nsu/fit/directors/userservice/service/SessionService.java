package ru.nsu.fit.directors.userservice.service;

import ru.nsu.fit.directors.userservice.model.Session;
import ru.nsu.fit.directors.userservice.model.User;
import ru.nsu.fit.directors.userservice.security.model.RefreshToken;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface SessionService {
    /**
     * Создать сессию.
     *
     * @param user         пользователь
     * @param refreshToken токен обновления
     * @return сессия
     */
    @Nonnull
    Session createSession(User user, RefreshToken refreshToken);

    /**
     * Обновить сессию.
     *
     * @param session      сессия для обновления
     * @param refreshToken токен обновления
     * @return сессия
     */
    @Nonnull
    Session updateSession(Session session, RefreshToken refreshToken);

    /**
     * Найти сессию по идентификатор.
     * @param tokenUuid идентификатор сессии
     * @return сессия или Optional.empty()
     */
    @Nonnull
    Optional<Session> findSessionByUuid(String tokenUuid);
}
