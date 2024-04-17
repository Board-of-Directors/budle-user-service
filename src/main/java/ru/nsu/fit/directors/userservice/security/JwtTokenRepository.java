package ru.nsu.fit.directors.userservice.security;

import jakarta.annotation.Nonnull;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.userservice.exception.UnauthorizedException;
import ru.nsu.fit.directors.userservice.security.claims.AccessTokenClaims;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenRepository {

    private static final String TOKEN_COOKIE_NAME = "refreshToken";
    private static final String HEADER_AUTH = "Authorization";
    private static final String USER_REFRESH = "/";
    private final JwtTokenProvider jwtTokenProvider;
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public Optional<Long> getUserId() {
        String authorizationHeader = request.getHeader(HEADER_AUTH);
        if (authorizationHeader != null && authorizationHeader.startsWith(JwtTokenProvider.SCHEMA_ALGORITHM)) {
            String token = authorizationHeader.substring(JwtTokenProvider.SCHEMA_ALGORITHM.length());
            jwtTokenProvider.validateToken(token, AccessTokenClaims.class);
            Optional<AccessTokenClaims> accessTokenClaims =
                jwtTokenProvider.validateToken(token, AccessTokenClaims.class);
            return accessTokenClaims.map(AccessTokenClaims::getUserId);
        }
        return Optional.empty();
    }

    @Nonnull
    public Long getUserIdOrThrow() {
        return getUserId().orElseThrow();
    }

    public void setTokenToCookie(String token) {
        response.setHeader(
            "Set-Cookie",
            String.format("%s=%s; SameSite=None; HttpOnly=true; Secure=true; Max-Age=%d; Path=%s;",
                TOKEN_COOKIE_NAME, token, 24 * 60 * 60, USER_REFRESH
            )
        );
    }

    @Nonnull
    public String getTokenFromCookie() {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(TOKEN_COOKIE_NAME)) {
                    return cookie.getValue();
                }
            }
        }
        throw new UnauthorizedException("Токен для обновления не найден", "Токен для обновления не найден");
    }
}
