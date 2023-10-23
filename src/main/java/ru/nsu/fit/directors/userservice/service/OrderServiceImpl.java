package ru.nsu.fit.directors.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.userservice.api.EstablishmentApi;
import ru.nsu.fit.directors.userservice.api.OrderApi;
import ru.nsu.fit.directors.userservice.dto.request.RequestOrderDto;
import ru.nsu.fit.directors.userservice.dto.response.ResponseOrderDto;
import ru.nsu.fit.directors.userservice.event.OrderCancelledEvent;
import ru.nsu.fit.directors.userservice.event.OrderEvent;
import ru.nsu.fit.directors.userservice.exception.OrderBookingTimeException;
import ru.nsu.fit.directors.userservice.mapper.OrderMapper;
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
    private final EstablishmentApi establishmentApi;
    private final OrderApi orderApi;
    private final OrderMapper orderMapper;

    @Override
    public void createOrder(RequestOrderDto order) {
        order.setUserId(securityService.getLoggedInUser().getId());
        validateOrderTime(order);
        kafkaTemplate.send("orderTopic", orderMapper.toEvent(order));
    }

    @Override
    public void cancelOrder(Long orderId) {
        validateUserOrder(orderId);
        kafkaTemplate.send("orderTopic", new OrderCancelledEvent()
            .setOrderId(orderId));
    }

    @Override
    public List<ResponseOrderDto> getOrders(Integer status) {
        User loggedUser = securityService.getLoggedInUser();
        return orderApi.syncListGetWithParams(
            uriBuilder -> uriBuilder.path("/order")
                .queryParam("userId", loggedUser.getId())
                .queryParam("status", status)
                .build(),
            ResponseOrderDto.class
        );

    }

    private void validateUserOrder(Long orderId) {
        var orders = getOrders(0);
        if (orders.stream()
            .map(ResponseOrderDto::getId)
            .noneMatch(id -> id.equals(orderId))) {
            throw new OrderManageException();

        }

    }

    private void validateOrderTime(RequestOrderDto requestOrderDto) {
        boolean isOrderTimeNotValid = Optional.ofNullable(
                establishmentApi.syncListGetWithParams(
                    uriBuilder -> uriBuilder.path("/establishment/internal/time")
                        .queryParam("establishmentId", requestOrderDto.getEstablishmentId())
                        .build(),
                    LocalDateTime.class
                )
            )
            .map(times -> !times.contains(requestOrderDto.getDate().atTime(requestOrderDto.getTime())))
            .orElse(true);
        if (isOrderTimeNotValid) {
            throw new OrderBookingTimeException();
        }

    }
}
