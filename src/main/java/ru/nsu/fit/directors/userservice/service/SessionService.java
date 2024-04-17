package ru.nsu.fit.directors.userservice.service;

import ru.nsu.fit.directors.userservice.model.Session;
import ru.nsu.fit.directors.userservice.model.User;
import ru.nsu.fit.directors.userservice.security.model.RefreshToken;

import java.util.Optional;

public interface SessionService {
    Session createSession(User user, RefreshToken refreshToken);

    Session updateSession(Session toUpdate, RefreshToken refreshToken);

    Optional<Session> findSessionByUuid(String tokenUuid);
}
