package ru.nsu.fit.directors.userservice.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsu.fit.directors.userservice.dto.CompanyDto;
import ru.nsu.fit.directors.userservice.dto.request.ReviewCreationDto;
import ru.nsu.fit.directors.userservice.dto.response.BaseResponse;
import ru.nsu.fit.directors.userservice.dto.response.EstablishmentListDto;
import ru.nsu.fit.directors.userservice.dto.response.ResponseReviewDto;
import ru.nsu.fit.directors.userservice.model.Company;

@FeignClient("establishment-service")
public interface EstablishmentServiceClient {
    @RequestMapping(method = RequestMethod.GET, value = "/establishment", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse<Company>> getCompanyById(@RequestParam Long establishmentId);

    @RequestMapping(method = RequestMethod.GET, value = "/internal/establishment", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse<List<CompanyDto>>> getCompaniesByIds(@RequestParam List<Long> ids);

    @RequestMapping(method = RequestMethod.GET, value = "/establishment/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse<EstablishmentListDto>> searchEstablishments(
        @RequestParam Optional<String> name,
        @RequestParam Optional<Boolean> hasMap,
        @RequestParam Optional<Boolean> hasCardPayment,
        @RequestParam Optional<String> category
    );

    @RequestMapping(method = RequestMethod.GET, value = "/internal/review", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ResponseReviewDto> getReviewsByExternalIds(@RequestParam List<Long> ids);

    @RequestMapping(method = RequestMethod.POST, value = "/internal/review", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Long createReview(@RequestBody ReviewCreationDto reviewCreationDto);

    @RequestMapping(method = RequestMethod.GET, value = "/establishment/internal/time", produces = MediaType.APPLICATION_JSON_VALUE)
    List<LocalDateTime> getValidTimes(@RequestParam Long establishmentId);
}
