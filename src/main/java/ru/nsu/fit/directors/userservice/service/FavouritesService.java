package ru.nsu.fit.directors.userservice.service;

import ru.nsu.fit.directors.userservice.model.Company;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public interface FavouritesService {
    void addToFavourites(Long establishmentId);

    void deleteFromFavourites(Long establishmentId);

    List<Company> getFavourites();
}
