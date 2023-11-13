package ru.nsu.fit.directors.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.userservice.api.EstablishmentApi;
import ru.nsu.fit.directors.userservice.dto.request.RequestGetEstablishmentParameters;
import ru.nsu.fit.directors.userservice.dto.response.EstablishmentListDto;
import ru.nsu.fit.directors.userservice.dto.response.ResponseBasicEstablishmentInfo;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@Component
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class EstablishmentServiceImpl implements EstablishmentService {
    private final EstablishmentApi establishmentApi;
    private final FavouritesService favouritesService;

    @Override
    public EstablishmentListDto getEstablishmentByParams(RequestGetEstablishmentParameters parameters) {
        List<ResponseBasicEstablishmentInfo> establishmentInfoList = establishmentApi.syncListGetWithParams(
            uriBuilder -> uriBuilder.path("/establishment/all")
                .queryParam("name", parameters.name())
                .queryParam("hasMap", parameters.hasMap())
                .queryParam("hasCardPayment", parameters.hasCardPayment())
                .queryParam("category", parameters.category())
                .build(),
            new ParameterizedTypeReference<>() {
            });
        establishmentInfoList.forEach(
            info -> info.setFavourite(favouritesService.getFavouritesIds().contains(info.getId()))
        );
        return new EstablishmentListDto(establishmentInfoList.size(), establishmentInfoList);
    }
}
