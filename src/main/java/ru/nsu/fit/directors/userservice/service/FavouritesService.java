package ru.nsu.fit.directors.userservice.service;

import ru.nsu.fit.directors.userservice.dto.CompanyDto;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import java.util.List;

@ParametersAreNonnullByDefault
public interface FavouritesService {
    /**
     * Добавить заведение в избранное.
     *
     * @param establishmentId идентификатор заведения
     */
    void addToFavourites(Long establishmentId);

    /**
     * Удалить заведение из избранного.
     *
     * @param establishmentId идентификатор заведения
     */
    void deleteFromFavourites(Long establishmentId);

    /**
     * Получить избранные заведения.
     *
     * @return список заведений
     */
    @Nonnull
    List<CompanyDto> getFavourites();

    /**
     * Получить идентификаторы заведений из избранного.
     *
     * @return список идентификаторов
     */
    @Nonnull
    List<Long> getFavouritesIds();
}
