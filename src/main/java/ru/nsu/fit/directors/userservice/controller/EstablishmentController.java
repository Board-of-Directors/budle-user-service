package ru.nsu.fit.directors.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.directors.userservice.dto.request.RequestGetEstablishmentParameters;
import ru.nsu.fit.directors.userservice.dto.response.EstablishmentListDto;
import ru.nsu.fit.directors.userservice.service.EstablishmentService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user/establishments")
@Validated
public class EstablishmentController {
    private final EstablishmentService establishmentService;
    /**
     * Get requests for establishments.
     * Can filter establishments by fields, also implemented sorting and pagination.
     *
     * @param parameters - list of get parameters for establishments
     * @return list of establishment dto, list size included.
     */
    @GetMapping(value = "all")
    public EstablishmentListDto getEstablishments(@Valid RequestGetEstablishmentParameters parameters) {
        return establishmentService.getEstablishmentByParams(parameters);
    }
}
