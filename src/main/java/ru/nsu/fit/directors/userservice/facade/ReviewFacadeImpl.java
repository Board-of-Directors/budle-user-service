package ru.nsu.fit.directors.userservice.facade;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.userservice.dto.request.ReviewCreationDto;
import ru.nsu.fit.directors.userservice.dto.response.ResponseReviewDto;
import ru.nsu.fit.directors.userservice.model.UserReview;
import ru.nsu.fit.directors.userservice.service.EstablishmentService;
import ru.nsu.fit.directors.userservice.service.ReviewService;
import ru.nsu.fit.directors.userservice.service.SecurityService;

@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class ReviewFacadeImpl implements ReviewFacade {
    private final ReviewService reviewService;
    private final SecurityService securityService;
    private final EstablishmentService establishmentService;

    @Override
    public void createReview(ReviewCreationDto reviewCreationDto) {
        Long externalId = establishmentService.createReview(reviewCreationDto);
        reviewService.save(new UserReview().setUser(securityService.getLoggedInUser()).setExternalId(externalId));
    }

    @Nonnull
    @Override
    public List<ResponseReviewDto> getReviews() {
        List<Long> reviewExternalIds = reviewService.getUserReviews(securityService.getLoggedInUser()).stream()
            .map(UserReview::getExternalId)
            .toList();
        return establishmentService.getReviewsByExternalIds(reviewExternalIds);
    }

}
