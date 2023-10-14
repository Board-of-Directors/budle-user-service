package ru.nsu.fit.directors.userservice.configuration;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientsConfiguration {
    public static final int TIMEOUT = 10000;
    private static final int SIZE = 16 * 1024 * 1024;

    @Bean
    @LoadBalanced
    public WebClient.Builder establishmentClientBuilder() {
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(SIZE))
            .build();

        return WebClient.builder()
            .baseUrl("http://establishment-service")
            .clientConnector(getReactorClientHttpConnector())
            .exchangeStrategies(strategies);
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder orderClientBuilder() {
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(SIZE))
            .build();
        return WebClient.builder()
            .baseUrl("http://order-service")
            .clientConnector(getReactorClientHttpConnector())
            .exchangeStrategies(strategies);
    }


    private ReactorClientHttpConnector getReactorClientHttpConnector() {
        return new ReactorClientHttpConnector(
            HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
                .doOnConnected(
                    connection -> {
                        connection.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
                        connection.addHandlerLast(new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
                    }
                )
        );
    }

}
