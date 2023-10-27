package ru.nsu.fit.directors.userservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.userservice.api.EstablishmentApi;
import ru.nsu.fit.directors.userservice.model.Company;

@Component
@RequiredArgsConstructor
public class CompanyRepository {
    private final EstablishmentApi establishmentApi;

    public Company getCompanyById(Long establishmentId) {
        return establishmentApi.syncGetWithParams(
            uriBuilder -> uriBuilder.path("/establishment").queryParam("establishmentId", establishmentId).build(),
            new ParameterizedTypeReference<>() {
            }
        );
    }

}
