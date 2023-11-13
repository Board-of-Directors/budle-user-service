package ru.nsu.fit.directors.userservice.service;

import ru.nsu.fit.directors.userservice.dto.request.RequestGetEstablishmentParameters;
import ru.nsu.fit.directors.userservice.dto.response.EstablishmentListDto;

public interface EstablishmentService {
    EstablishmentListDto getEstablishmentByParams(RequestGetEstablishmentParameters parameters);
}
