package ru.nsu.fit.directors.userservice.security.claims;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class RefreshTokenClaims extends SMHTokenClaims {

    private static final String CLAIM_TOKEN = "token";

    public RefreshTokenClaims(Claims claims) {
        super(claims);
    }

    public String getToken() {
        return claims.get(CLAIM_TOKEN, String.class);
    }

    public static Claims build(String username, String tokenUuid) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(RefreshTokenClaims.CLAIM_TOKEN, tokenUuid);
        return claims;
    }
}
