package ru.nsu.fit.directors.userservice.controller;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.directors.userservice.dto.request.ReviewCreationDto;
import ru.nsu.fit.directors.userservice.dto.response.ResponseReviewDto;
import ru.nsu.fit.directors.userservice.facade.ReviewFacade;

@RestController
@RequestMapping(value = "/user/review")
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class ReviewController {
    private final ReviewFacade reviewFacade;

    @PostMapping
    public void create(@RequestBody @Valid ReviewCreationDto reviewCreationDto) {
        reviewFacade.createReview(reviewCreationDto);
    }

    @GetMapping
    public List<ResponseReviewDto> get() {
        return reviewFacade.getReviews();
    }
}
