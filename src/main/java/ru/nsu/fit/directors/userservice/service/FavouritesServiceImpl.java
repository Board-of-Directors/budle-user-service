package ru.nsu.fit.directors.userservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.userservice.model.Company;
import ru.nsu.fit.directors.userservice.model.User;
import ru.nsu.fit.directors.userservice.repository.CompanyRepository;
import ru.nsu.fit.directors.userservice.repository.CompanyService;
import ru.nsu.fit.directors.userservice.repository.UserRepository;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@RequiredArgsConstructor
@Component
@ParametersAreNonnullByDefault
public class FavouritesServiceImpl implements FavouritesService {
    private final SecurityService securityService;
    private final UserRepository userRepository;
    private final CompanyService companyService;
    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public void addToFavourites(Long establishmentId) {
        User loggedUser = securityService.getLoggedInUser();
        Company company = companyService.getCompanyById(establishmentId);
        company = companyRepository.save(company);
        loggedUser.getFavourites().add(company);
        userRepository.save(loggedUser);
    }

    @Override
    @Transactional
    public void deleteFromFavourites(Long establishmentId) {
        User loggedUser = securityService.getLoggedInUser();
        loggedUser.getFavourites().remove(new Company().setId(establishmentId));
        userRepository.save(loggedUser);
    }

    @Override
    @Transactional
    public List<Company> getFavourites() {
        User loggedUser = securityService.getLoggedInUser();
        return companyService.getCompaniesByIds(loggedUser.getFavourites().stream().map(Company::getId));
    }
}
