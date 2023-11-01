package ru.nsu.fit.directors.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.directors.userservice.model.Company;
import ru.nsu.fit.directors.userservice.service.FavouritesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user/favourite")
@CrossOrigin(allowCredentials = "true", originPatterns = {"*"}, allowedHeaders = {"*"})
public class FavouritesController {
    private final FavouritesService favouritesService;

    @GetMapping
    public List<Company> getFavourites() {
        return favouritesService.getFavourites();
    }

    @PostMapping
    public void addToFavourites(Long establishmentId) {
        favouritesService.addToFavourites(establishmentId);
    }

    @PutMapping
    public void deleteFromFavourites(Long establishmentId) {
        favouritesService.deleteFromFavourites(establishmentId);
    }

}
