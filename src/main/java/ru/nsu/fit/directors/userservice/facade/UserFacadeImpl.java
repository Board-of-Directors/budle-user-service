package ru.nsu.fit.directors.userservice.facade;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.userservice.dto.AuthResponse;
import ru.nsu.fit.directors.userservice.dto.request.RequestUserDto;
import ru.nsu.fit.directors.userservice.dto.response.ResponseUserDto;
import ru.nsu.fit.directors.userservice.exception.UnauthorizedException;
import ru.nsu.fit.directors.userservice.mapper.UserMapper;
import ru.nsu.fit.directors.userservice.model.Session;
import ru.nsu.fit.directors.userservice.model.User;
import ru.nsu.fit.directors.userservice.security.JwtTokenProvider;
import ru.nsu.fit.directors.userservice.security.claims.RefreshTokenClaims;
import ru.nsu.fit.directors.userservice.security.model.RefreshToken;
import ru.nsu.fit.directors.userservice.service.SessionService;
import ru.nsu.fit.directors.userservice.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class UserFacadeImpl implements UserFacade {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final SessionService sessionService;

    @Nonnull
    @Override
    public ResponseUserDto getLoggedInUser() {
        return userMapper.toResponse(userService.getLoggedInUser());
    }

    @Override
    public void registerUser(RequestUserDto userDto) {
        User user = userMapper.toModel(userDto).setPassword(passwordEncoder.encode(userDto.getPassword()));
        userService.save(user);
    }

    @Nonnull
    @Override
    public AuthResponse loginCredentials(RequestUserDto userCredentials) {
        User user = userService.getByPhoneNumber(userCredentials.getPhoneNumber());
        if (!passwordEncoder.matches(userCredentials.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Не вошел в аккаунт", "Не вошел в аккаунт");
        }
        RefreshToken refreshToken = jwtTokenProvider.createRefreshToken(userCredentials.getPhoneNumber());
        sessionService.createSession(user, refreshToken);
        return new AuthResponse(
            jwtTokenProvider.createAccessToken(userCredentials.getPhoneNumber(), user, 1234L),
            refreshToken.getToken()
        );
    }

    @Nonnull
    @Override
    @Transactional
    public AuthResponse refreshToken(String refreshToken) {
        log.info("refresh: started with request {}", refreshToken);
        RefreshTokenClaims claims = jwtTokenProvider.validateToken(refreshToken, RefreshTokenClaims.class)
            .orElseThrow(UnauthorizedException::new);

        Session session = sessionService.findSessionByUuid(claims.getToken())
            .orElseThrow(() -> new UnauthorizedException("Не найдено", "Не найдено"));

        return refreshAuthenticationResponseDto(claims, session);
    }

    @Nonnull
    private AuthResponse refreshAuthenticationResponseDto(RefreshTokenClaims claims, Session session) {
        log.info("refresh authentication response dto");
        String username = claims.getUsername();

        RefreshToken newRefreshTokenInfo = jwtTokenProvider.createRefreshToken(username);
        User user = session.getUser();
        String newAccessToken = jwtTokenProvider.createAccessToken(username, user, session.getId());

        sessionService.updateSession(session, newRefreshTokenInfo);
        return AuthResponse.builder()
            .accessToken(newAccessToken)
            .refreshToken(newRefreshTokenInfo.getToken())
            .build();
    }
}
