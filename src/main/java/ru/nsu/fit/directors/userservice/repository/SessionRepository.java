package ru.nsu.fit.directors.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.fit.directors.userservice.model.Session;
import ru.nsu.fit.directors.userservice.model.User;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByRefreshTokenUuid(String tokenUuid);

    Optional<Session> findByUser(User user);
}
