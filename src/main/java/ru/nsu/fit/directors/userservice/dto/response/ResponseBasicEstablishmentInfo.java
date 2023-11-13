package ru.nsu.fit.directors.userservice.dto.response;

import lombok.Data;

@Data
public class ResponseBasicEstablishmentInfo {
    private Long id;
    private String name;
    private String category;
    private Float rating;
    private String address;
    private String image;
    private Boolean hasMap;
    private Boolean hasCardPayment;
    private String starsCount;
    private String cuisineCountry;
    private boolean isFavourite;
}
