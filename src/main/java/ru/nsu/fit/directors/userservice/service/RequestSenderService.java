package ru.nsu.fit.directors.userservice.service;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface RequestSenderService {
    @Nonnull
    Map<String, Object> sendRequest(String phoneNumber);
}
