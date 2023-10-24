package ru.nsu.fit.directors.userservice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class OrderApi extends AbstractApi {
    @Autowired
    public OrderApi(WebClient.Builder orderClientBuilder) {
        super(orderClientBuilder);
    }
}
