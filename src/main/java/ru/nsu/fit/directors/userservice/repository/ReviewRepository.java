package ru.nsu.fit.directors.userservice.repository;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.fit.directors.userservice.model.User;
import ru.nsu.fit.directors.userservice.model.UserReview;

public interface ReviewRepository extends JpaRepository<UserReview, Long> {
    @Nonnull
    List<UserReview> findAllByUser(User user);
}
