package ru.nsu.fit.directors.userservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.userservice.api.EstablishmentServiceClient;
import ru.nsu.fit.directors.userservice.dto.CompanyDto;
import ru.nsu.fit.directors.userservice.dto.response.BaseResponse;
import ru.nsu.fit.directors.userservice.enums.EntityType;
import ru.nsu.fit.directors.userservice.exception.EntityNotFoundException;
import ru.nsu.fit.directors.userservice.model.Company;
import ru.nsu.fit.directors.userservice.model.User;
import ru.nsu.fit.directors.userservice.repository.CompanyRepository;
import ru.nsu.fit.directors.userservice.repository.UserRepository;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class FavouritesServiceImpl implements FavouritesService {
    private final SecurityService securityService;
    private final UserRepository userRepository;
    private final EstablishmentServiceClient establishmentServiceClient;
    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public void addToFavourites(Long establishmentId) {
        User loggedUser = securityService.getLoggedInUser();
        Company company = Optional.ofNullable(establishmentServiceClient.getCompanyById(establishmentId).getBody())
            .map(BaseResponse::getResult)
            .orElseThrow(() -> new EntityNotFoundException(EntityType.COMPANY, establishmentId));
        company = companyRepository.save(company);
        if (!loggedUser.getFavourites().contains(company)) {
            loggedUser.getFavourites().add(company);
            userRepository.save(loggedUser);
        }
    }

    @Override
    @Transactional
    public void deleteFromFavourites(Long establishmentId) {
        User loggedUser = securityService.getLoggedInUser();
        loggedUser.getFavourites().remove(companyRepository.findById(establishmentId).orElseThrow());
        userRepository.save(loggedUser);
    }

    @Nonnull
    @Override
    @Transactional
    public List<CompanyDto> getFavourites() {
        User loggedUser = securityService.getLoggedInUser();
        Set<Long> favouriteIds = loggedUser.getFavourites().stream().map(Company::getId).collect(Collectors.toSet());
        return Optional.ofNullable(establishmentServiceClient.getCompaniesByIds(favouriteIds).getBody())
            .map(BaseResponse::getResult)
            .orElseGet(List::of);
    }

    @Nonnull
    @Override
    public List<Long> getFavouritesIds() {
        try {
            User loggedUser = securityService.getLoggedInUser();
            return loggedUser.getFavourites().stream().map(Company::getId).toList();
        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }

}
