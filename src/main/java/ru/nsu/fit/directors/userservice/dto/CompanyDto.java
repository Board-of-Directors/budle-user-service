package ru.nsu.fit.directors.userservice.dto;

public record CompanyDto(
    Float rating,
    String address,
    String image,
    Boolean hasMap,
    Boolean hasCardPayment,
    String name,
    Long id,
    String category,
    String cuisineCountry,
    String starsCount
) {
}
