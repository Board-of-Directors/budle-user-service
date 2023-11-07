package ru.nsu.fit.directors.userservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.userservice.api.EstablishmentApi;
import ru.nsu.fit.directors.userservice.model.Company;

import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class CompanyService {
    private final EstablishmentApi establishmentApi;

    public Company getCompanyById(Long establishmentId) {
        return establishmentApi.syncGetWithParams(
            uriBuilder -> uriBuilder.path("/establishment").queryParam("establishmentId", establishmentId).build(),
            new ParameterizedTypeReference<>() {
            }
        );
    }

    public List<Company> getCompaniesByIds(Stream<Long> ids) {
        return establishmentApi.syncListGetWithParams(
            uriBuilder -> uriBuilder.path("/internal/establishment").queryParam("ids", ids).build(),
            new ParameterizedTypeReference<>() {}
        );
    }
}
