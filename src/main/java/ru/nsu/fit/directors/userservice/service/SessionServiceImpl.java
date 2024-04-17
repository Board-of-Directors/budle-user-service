package ru.nsu.fit.directors.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.fit.directors.userservice.model.Session;
import ru.nsu.fit.directors.userservice.model.User;
import ru.nsu.fit.directors.userservice.repository.SessionRepository;
import ru.nsu.fit.directors.userservice.security.model.RefreshToken;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    @Transactional
    @Override
    public Session createSession(User user, RefreshToken refreshToken) {
        log.info("createSession: start create session");
        var sessionOpt = sessionRepository.findByUser(user);

        if (sessionOpt.isPresent())
            return updateSession(sessionOpt.get(), refreshToken);

        return sessionRepository.save(new Session()
            .setUser(user)
            .setRefreshTokenUuid(refreshToken.getUuid())
            .setExpireAt(refreshToken.getExpireAt())
            .setCreateAt(Instant.now())
            .setUpdateAt(Instant.now()));
    }

    @Override
    public Session updateSession(Session toUpdate, RefreshToken refreshToken) {
        log.info("updateSession: start update session and save");
        toUpdate.setRefreshTokenUuid(refreshToken.getUuid());
        toUpdate.setExpireAt(refreshToken.getExpireAt());
        return sessionRepository.save(toUpdate);
    }

    @Override
    public Optional<Session> findSessionByUuid(String tokenUuid) {
        return this.sessionRepository.findByRefreshTokenUuid(tokenUuid);
    }
}
