package ru.nsu.fit.directors.userservice.facade;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import ru.nsu.fit.directors.userservice.dto.AuthResponse;
import ru.nsu.fit.directors.userservice.dto.request.RequestUserDto;
import ru.nsu.fit.directors.userservice.dto.response.ResponseUserDto;

@ParametersAreNonnullByDefault
public interface UserFacade {
    /**
     * Получить активного пользователя.
     *
     * @return данные об активном пользователе.
     */
    @Nonnull
    ResponseUserDto getLoggedInUser();

    /**
     * Зарегестрировать пользователя.
     *
     * @param requestUserDto данные о пользователе
     */
    void registerUser(RequestUserDto requestUserDto);

    /**
     * Выполнить вход используя данные пользователя.
     *
     * @param userCredentials данные пользователя
     * @return токены
     */
    @Nonnull
    AuthResponse loginCredentials(RequestUserDto userCredentials);

    /**
     * Обновить токены входа и обновления.
     *
     * @param refreshToken токен обновления
     * @return новые токены
     */
    @Nonnull
    AuthResponse refreshToken(String refreshToken);
}
