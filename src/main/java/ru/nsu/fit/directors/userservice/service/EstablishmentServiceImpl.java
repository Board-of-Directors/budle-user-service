package ru.nsu.fit.directors.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.userservice.api.EstablishmentServiceClient;
import ru.nsu.fit.directors.userservice.dto.request.RequestGetEstablishmentParameters;
import ru.nsu.fit.directors.userservice.dto.request.ReviewCreationDto;
import ru.nsu.fit.directors.userservice.dto.response.BaseResponse;
import ru.nsu.fit.directors.userservice.dto.response.EstablishmentListDto;
import ru.nsu.fit.directors.userservice.dto.response.ResponseReviewDto;
import ru.nsu.fit.directors.userservice.enums.EntityType;
import ru.nsu.fit.directors.userservice.exception.EntityNotFoundException;

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
        EstablishmentListDto establishmentInfoList = Optional.ofNullable(search(parameters).getBody())
            .map(BaseResponse::getResult)
            .orElseGet(this::emptyList);
        establishmentInfoList.establishments().forEach(
            info -> info.setFavourite(favouritesService.getFavouritesIds().contains(info.getId()))
        );
        return establishmentInfoList;
    }

    @Nonnull
    private EstablishmentListDto emptyList() {
        return new EstablishmentListDto(0, List.of());
    }

    @Nonnull
    private ResponseEntity<BaseResponse<EstablishmentListDto>> search(RequestGetEstablishmentParameters parameters) {
        return establishmentClient.searchEstablishments(
            Optional.ofNullable(parameters.name()),
            Optional.ofNullable(parameters.hasMap()),
            Optional.ofNullable(parameters.hasCardPayment()),
            Optional.ofNullable(parameters.category())
        );
    }

    @Nonnull
    @Override
    public List<ResponseReviewDto> getReviewsByExternalIds(List<Long> externalIds) {
        return Optional.ofNullable(establishmentClient.getReviewsByExternalIds(externalIds).getBody())
            .map(BaseResponse::getResult)
            .orElseGet(List::of);
    }

    @Nonnull
    @Override
    public Long createReview(ReviewCreationDto reviewCreationDto) {
        return Optional.ofNullable(establishmentClient.createReview(reviewCreationDto).getBody())
            .map(BaseResponse::getResult)
            .orElseThrow(() -> new EntityNotFoundException(EntityType.COMPANY, reviewCreationDto.establishmentId()));
    }
}
