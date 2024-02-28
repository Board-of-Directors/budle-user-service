package ru.nsu.fit.directors.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.userservice.api.EstablishmentServiceClient;
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
    private final EstablishmentServiceClient establishmentClient;
    private final FavouritesService favouritesService;

    @Nonnull
    @Override
    public EstablishmentListDto getEstablishmentByParams(RequestGetEstablishmentParameters parameters) {
        EstablishmentListDto establishmentInfoList = establishmentClient.searchEstablishments(
            Optional.ofNullable(parameters.name()),
            Optional.ofNullable(parameters.hasMap()),
            Optional.ofNullable(parameters.hasCardPayment()),
            Optional.ofNullable(parameters.category())
        );
        establishmentInfoList.establishments().forEach(
            info -> info.setFavourite(favouritesService.getFavouritesIds().contains(info.getId()))
        );
        return establishmentInfoList;
    }

    @Nonnull
    @Override
    public List<ResponseReviewDto> getReviewsByExternalIds(List<Long> externalIds) {
        return establishmentClient.getReviewsByExternalIds(externalIds);
    }

    @Nonnull
    @Override
    public Long createReview(ReviewCreationDto reviewCreationDto) {
        return establishmentClient.createReview(reviewCreationDto);
    }
}
