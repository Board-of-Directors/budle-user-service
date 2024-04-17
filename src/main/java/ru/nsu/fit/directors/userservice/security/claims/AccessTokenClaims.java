package ru.nsu.fit.directors.userservice.security.claims;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import ru.nsu.fit.directors.userservice.configuration.SecurityProperties;
import ru.nsu.fit.directors.userservice.model.User;

public class AccessTokenClaims extends SMHTokenClaims {

    private SecurityProperties.KeyClaims keyClaims;

    public AccessTokenClaims(Claims claims) {
        super(claims);
    }

    public AccessTokenClaims(Claims claims, SecurityProperties.KeyClaims keyClaims) {
        this(claims);
        this.keyClaims = keyClaims;
    }

    public Claims build(String username, User user, Long sessionId) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(keyClaims.getUserId(), user.getId());
        claims.put(keyClaims.getSessionId(), sessionId);
        return claims;
    }
}
