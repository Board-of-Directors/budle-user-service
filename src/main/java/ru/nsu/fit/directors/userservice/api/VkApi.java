package ru.nsu.fit.directors.userservice.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.nsu.fit.directors.userservice.dto.request.RequestVkNotification;
import ru.nsu.fit.directors.userservice.dto.response.VkNotificationResponse;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class VkApi {
    private final WebClient vkApiClient;

    public List<VkNotificationResponse> sendNotification(RequestVkNotification requestVkNotification) {
        ParameterizedTypeReference<List<VkNotificationResponse>> reference = new ParameterizedTypeReference<>() {};
        return vkApiClient.post()
            .uri(uriBuilder -> uriBuilder.path("/method/notifications.sendMessage").build())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestVkNotification)
            .retrieve()
            .toEntity(reference)
            .log()
            .block()
            .getBody();
    }

}
