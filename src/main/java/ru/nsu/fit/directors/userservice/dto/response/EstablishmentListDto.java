package ru.nsu.fit.directors.userservice.dto.response;

import java.util.List;

public record EstablishmentListDto(
    int count,
    List<ResponseBasicEstablishmentInfo> establishmentInfoList
) {
}
