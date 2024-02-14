package ru.nsu.fit.directors.userservice.service;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import ru.nsu.fit.directors.userservice.dto.request.RequestGetEstablishmentParameters;
import ru.nsu.fit.directors.userservice.dto.response.EstablishmentListDto;
import ru.nsu.fit.directors.userservice.dto.response.ResponseReviewDto;

@ParametersAreNonnullByDefault
public interface EstablishmentService {
    /**
     * Получить заведение исходя из параметров.
     *
     * @param parameters параметры и фильтры
     * @return список заведений, удовлетворяющих фильтрам
     */
    @Nonnull
    EstablishmentListDto getEstablishmentByParams(RequestGetEstablishmentParameters parameters);

    /**
     * Получить отзывы по внешним идентфикаторам.
     *
     * @param externalIds внешние идентификаторы отзывов
     * @return список отзывов
     */
    @Nonnull
    List<ResponseReviewDto> getReviewsByExternalIds(List<Long> externalIds);
}
