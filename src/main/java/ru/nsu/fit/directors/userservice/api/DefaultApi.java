package ru.nsu.fit.directors.userservice.api;

import brave.internal.Nullable;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface DefaultApi {

    @Nullable
    <T> T syncGetWithParams(String path, Map<String, ?> params, Class<T> type);

    @Nullable
    <T> List<T> syncListGetWithParams(Function<UriBuilder, URI> uriBuilder, Class<T> type);

}
