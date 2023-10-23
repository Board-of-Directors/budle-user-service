package ru.nsu.fit.directors.userservice.api;

import brave.internal.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import ru.nsu.fit.directors.userservice.exception.ClientException;
import ru.nsu.fit.directors.userservice.exception.ServerNotAvailableException;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class OrderApi implements DefaultApi {
    private final WebClient.Builder orderClientBuilder;

    @Nullable
    public <T> T syncGetWithParams(String path, Map<String, ?> params, Class<T> type) {
        return Optional.ofNullable(orderClientBuilder.build()
                .get()
                .uri(path, params)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, response -> response.bodyToMono(String.class).map(ServerNotAvailableException::new))
                .toEntity(type)
                .block())
            .map(ResponseEntity::getBody)
            .orElse(null);
    }

    @Nullable
    public <T> List<T> syncListGetWithParams(Function<UriBuilder, URI> uriBuilder, Class<T> type) {
        return Optional.ofNullable(orderClientBuilder.build()
                .get()
                .uri(uriBuilder)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class).map(ClientException::new))
                .onStatus(HttpStatusCode::is5xxServerError, response -> response.bodyToMono(String.class).map(ServerNotAvailableException::new))
                .toEntityList(type)
                .block())
            .map(ResponseEntity::getBody)
            .orElse(null);
    }

}
