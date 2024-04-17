package ru.nsu.fit.directors.userservice.security.claims;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SMHTokenClaims {
    private static final String USER_ID = "userID";

    protected final Claims claims;

    public String getUsername() {
        return claims.getSubject();
    }

    public Long getUserId() {
        return claims.get(USER_ID, Long.class);
    }
}
