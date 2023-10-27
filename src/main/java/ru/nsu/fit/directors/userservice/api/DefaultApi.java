package ru.nsu.fit.directors.userservice.api;

import brave.internal.Nullable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.util.UriBuilder;
import ru.nsu.fit.directors.userservice.dto.response.BaseResponse;

import java.net.URI;
import java.util.List;
import java.util.function.Function;

public interface DefaultApi {

    @Nullable
    <T> T syncGetWithParams(Function<UriBuilder, URI> uriBuilder, ParameterizedTypeReference<BaseResponse<T>> reference);

    @Nullable
    <T> List<T> syncListGetWithParams(Function<UriBuilder, URI> uriBuilder, ParameterizedTypeReference<BaseResponse<List<T>>> reference);

}
