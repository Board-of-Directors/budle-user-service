package ru.nsu.fit.directors.userservice.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
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
            .uri(uriBuilder -> uriBuilder.path("/method/notifications.sendMessage").build())
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(requestVkNotification), RequestVkNotification.class)
            .retrieve()
            .toEntity(reference)
            .log()
            .block()
            .getBody();
    }

}
