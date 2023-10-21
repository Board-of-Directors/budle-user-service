package ru.nsu.fit.directors.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.nsu.fit.directors.userservice.dto.request.RequestOrderDto;
import ru.nsu.fit.directors.userservice.dto.response.ResponseOrderDto;
import ru.nsu.fit.directors.userservice.event.OrderCancelledEvent;
import ru.nsu.fit.directors.userservice.event.OrderCreatedEvent;
import ru.nsu.fit.directors.userservice.event.OrderEvent;
import ru.nsu.fit.directors.userservice.model.User;

import javax.annotation.ParametersAreNonnullByDefault;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class OrderServiceImpl implements OrderService {
    private final SecurityService securityService;
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    private final WebClient.Builder orderClientBuilder;
    private final WebClient.Builder establishmentClientBuilder;

    @Override
    public void createOrder(RequestOrderDto order) {
        User loggedUser = securityService.getLoggedInUser();
        order.setUserId(loggedUser.getId());
        validateOrderTime(order);
        kafkaTemplate.send("orderTopic", toEvent(order));
    }

    private OrderCreatedEvent toEvent(RequestOrderDto order) {
        return new OrderCreatedEvent()
            .setGuestCount(order.getGuestCount())
            .setDate(order.getDate())
            .setTime(order.getTime())
            .setUserId(order.getUserId())
            .setEstablishmentId(order.getEstablishmentId())
            .setSpotId(order.getSpotId());
    }

    @Override
    public void cancelOrder(Long orderId) {
        validateUserOrder(orderId);
        kafkaTemplate.send("orderTopic", new OrderCancelledEvent()
            .setOrderId(orderId));
    }

    @Override
    public List<ResponseOrderDto> getOrders(Integer status) {
        ParameterizedTypeReference<List<ResponseOrderDto>> ref = new ParameterizedTypeReference<>() {
        };
        User loggedUser = securityService.getLoggedInUser();
        return Optional.ofNullable(orderClientBuilder.build()
            .get()
            .uri(uriBuilder -> uriBuilder.path("/order")
                .queryParam("userId", loggedUser.getId())
                .queryParam("status", status)
                .build())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntity(ref)
            .block()).map(HttpEntity::getBody).orElse(null);

    }

    private void validateUserOrder(Long orderId) {
        var orders = getOrders(0);
        if (orders.stream()
            .map(ResponseOrderDto::getId)
            .noneMatch(id -> id.equals(orderId))) {
            throw new RuntimeException("You cannot do this");

        }

    }

    private void validateOrderTime(RequestOrderDto requestOrderDto) {
        ParameterizedTypeReference<List<LocalDateTime>> ref = new ParameterizedTypeReference<>() {
        };
        boolean isOrderTimeNotValid = Optional.ofNullable(establishmentClientBuilder.build()
                .get()
                .uri(uriBuilder -> uriBuilder.path("establishment/internal/time")
                    .queryParam("establishmentId", requestOrderDto.getEstablishmentId())
                    .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(ref)
                .block())
            .map(HttpEntity::getBody)
            .map(times -> !times.contains(requestOrderDto.getDate().atTime(requestOrderDto.getTime())))
            .orElse(true);
        if (isOrderTimeNotValid) {
            throw new RuntimeException("Not valid time");

        }

    }
}
