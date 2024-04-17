package ru.nsu.fit.directors.userservice.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "security.jwt", ignoreUnknownFields = false)
public class SecurityProperties {

    private String secret;

    private int accessTokenValiditySeconds;

    private int refreshTokenValidityDays;

    private String headerAlg;

    private String userId;

    private String userRole;

    private String sessionId;

    @NestedConfigurationProperty
    private KeyClaims keyClaims = new KeyClaims(userId, userRole, sessionId);

    @NestedConfigurationProperty
    private TokenValidity regular = new TokenValidity(Duration.ofSeconds(accessTokenValiditySeconds),
        Duration.ofDays(refreshTokenValidityDays));

    @Getter
    @Setter
    @AllArgsConstructor
    public static class TokenValidity {
        @DurationUnit(ChronoUnit.SECONDS)
        private Duration accessTokenValiditySeconds;

        @DurationUnit(ChronoUnit.DAYS)
        private Duration refreshTokenValidityDays;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class KeyClaims {
        private String userId;

        private String userRole;

        private String sessionId;
    }
}
