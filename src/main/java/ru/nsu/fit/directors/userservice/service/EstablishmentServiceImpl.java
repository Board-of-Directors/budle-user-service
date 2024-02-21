package ru.nsu.fit.directors.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.userservice.api.EstablishmentApi;
import ru.nsu.fit.directors.userservice.dto.request.RequestGetEstablishmentParameters;
import ru.nsu.fit.directors.userservice.dto.request.ReviewCreationDto;
import ru.nsu.fit.directors.userservice.dto.response.EstablishmentListDto;
import ru.nsu.fit.directors.userservice.dto.response.ResponseReviewDto;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class EstablishmentServiceImpl implements EstablishmentService {
    private final EstablishmentApi establishmentApi;
    private final FavouritesService favouritesService;

    @Nonnull
    @Override
    public EstablishmentListDto getEstablishmentByParams(RequestGetEstablishmentParameters parameters) {
        EstablishmentListDto establishmentInfoList = establishmentApi.syncGetWithParams(
            uriBuilder -> uriBuilder.path("/establishment/all")
                .queryParamIfPresent("name", Optional.ofNullable(parameters.name()))
                .queryParamIfPresent("hasMap", Optional.ofNullable(parameters.hasMap()))
                .queryParamIfPresent("hasCardPayment", Optional.ofNullable(parameters.hasCardPayment()))
                .queryParamIfPresent("category", Optional.ofNullable(parameters.category()))
                .build(),
            new ParameterizedTypeReference<>() {
            }
        );
        establishmentInfoList.establishments().forEach(
            info -> info.setFavourite(favouritesService.getFavouritesIds().contains(info.getId()))
        );
        return establishmentInfoList;
    }

    @Nonnull
    @Override
    public List<ResponseReviewDto> getReviewsByExternalIds(List<Long> externalIds) {
        return establishmentApi.syncListGetWithParams(
            uriBuilder -> uriBuilder.path("/internal/review")
                .queryParam("ids", externalIds)
                .build(),
            new ParameterizedTypeReference<>() {
            }
        );
    }

    @Nonnull
    @Override
    public Long createReview(ReviewCreationDto reviewCreationDto) {
        return establishmentApi.syncPostWithParams(
            uriBuilder -> uriBuilder.path("/internal/review").build(),
            reviewCreationDto,
            new ParameterizedTypeReference<>() {
            },
            new ParameterizedTypeReference<>() {
            }
        );

    }
}
