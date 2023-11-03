package ru.nsu.fit.directors.userservice.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.nsu.fit.directors.userservice.dto.request.RequestVkNotification;
import ru.nsu.fit.directors.userservice.dto.response.VkNotificationResponse;

@Component
@RequiredArgsConstructor
@Slf4j
public class VkApi {
    private final WebClient vkApiClient;

    public VkNotificationResponse sendNotification(RequestVkNotification requestVkNotification) {
        log.info("Send vk notification {}", requestVkNotification);
        ParameterizedTypeReference<VkNotificationResponse> reference = new ParameterizedTypeReference<>() {};
        return vkApiClient.post()
            .uri(uriBuilder -> uriBuilder.path("/method/notifications.sendMessage")
                .queryParam("v", "5.154")
                .queryParam("access_token", requestVkNotification.access_token())
                .queryParam("user_ids", requestVkNotification.user_ids())
                .queryParam("message", requestVkNotification.message())
                .build())
            .retrieve()
            .toEntity(reference)
            .log()
            .block()
            .getBody();
    }

}
