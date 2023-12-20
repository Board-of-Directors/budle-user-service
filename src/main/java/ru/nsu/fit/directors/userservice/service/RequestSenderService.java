package ru.nsu.fit.directors.userservice.service;

import java.util.Map;

public interface RequestSenderService {
    Map<String, Object> sendRequest(String phoneNumber);
}
