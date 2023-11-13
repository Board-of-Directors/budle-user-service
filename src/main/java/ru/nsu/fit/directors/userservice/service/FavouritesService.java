package ru.nsu.fit.directors.userservice.service;

import ru.nsu.fit.directors.userservice.dto.CompanyDto;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public interface FavouritesService {
    void addToFavourites(Long establishmentId);

    void deleteFromFavourites(Long establishmentId);

    List<CompanyDto> getFavourites();

    List<Long> getFavouritesIds();
}
