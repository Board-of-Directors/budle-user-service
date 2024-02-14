package ru.nsu.fit.directors.userservice.service;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.userservice.model.User;
import ru.nsu.fit.directors.userservice.model.UserReview;
import ru.nsu.fit.directors.userservice.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Override
    public void save(UserReview userReview) {
        reviewRepository.save(userReview);
    }

    @Nonnull
    @Override
    public List<UserReview> getUserReviews(User user) {
        return reviewRepository.findAllByUser(user);
    }
}
