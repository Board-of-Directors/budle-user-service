package ru.nsu.fit.directors.userservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.userservice.api.EstablishmentServiceClient;
import ru.nsu.fit.directors.userservice.dto.CompanyDto;
import ru.nsu.fit.directors.userservice.model.Company;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CompanyService {
    private final EstablishmentServiceClient establishmentServiceClient;

    public Company getCompanyById(Long establishmentId) {
        return establishmentServiceClient.getCompanyById(establishmentId).getBody().getResult();
    }

    public List<CompanyDto> getCompaniesByIds(List<Long> ids) {
        return establishmentServiceClient.getCompaniesByIds(ids).getBody().getResult();
    }
}
