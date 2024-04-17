package ru.nsu.fit.directors.userservice.security.model;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class RefreshToken {

    private String uuid;

    private String token;

    private Instant expireAt;
}
