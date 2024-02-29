package ru.nsu.fit.directors.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.userservice.api.EstablishmentServiceClient;
import ru.nsu.fit.directors.userservice.api.OrderServiceClient;
import ru.nsu.fit.directors.userservice.dto.request.RequestOrderDto;
import ru.nsu.fit.directors.userservice.dto.response.ResponseOrderDto;
import ru.nsu.fit.directors.userservice.event.OrderCancelledEvent;
import ru.nsu.fit.directors.userservice.event.OrderConfirmedEvent;
import ru.nsu.fit.directors.userservice.event.OrderEvent;
import ru.nsu.fit.directors.userservice.exception.OrderBookingTimeException;
import ru.nsu.fit.directors.userservice.exception.OrderManageException;
import ru.nsu.fit.directors.userservice.mapper.OrderMapper;
import ru.nsu.fit.directors.userservice.model.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class OrderServiceImpl implements OrderService {
    private final SecurityService securityService;
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    private final EstablishmentServiceClient establishmentServiceClient;
    private final OrderServiceClient orderServiceClient;
    private final OrderMapper orderMapper;

    @Override
    public void createOrder(RequestOrderDto order) {
        validateOrderTime(order);
        kafkaTemplate.send("orderTopic", orderMapper.toEvent(order, securityService.getLoggedInUser()));
    }

    @Override
    public void cancelOrder(Long orderId) {
        validateUserOrder(orderId);
        kafkaTemplate.send("orderTopic", new OrderCancelledEvent()
            .setOrderId(orderId));
    }

    @Override
    @Nonnull
    public List<ResponseOrderDto> getOrders(@Nullable Integer status) {
        User loggedUser = securityService.getLoggedInUser();
        return orderServiceClient.getUserOrders(loggedUser.getId(), Optional.ofNullable(status)).getBody().getResult();
    }

    @Override
    public void confirmOrder(Long orderId) {
        validateUserOrder(orderId);
        kafkaTemplate.send("orderTopic", new OrderConfirmedEvent()
            .setOrderId(orderId));
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
        LocalDateTime bookingTime = requestOrderDto.getDate().atTime(requestOrderDto.getTime());
        List<LocalDateTime> validTimes = Optional.of(getValidTimes(requestOrderDto))
            .orElse(Collections.emptyList());
        if (!validTimes.contains(bookingTime)) {
            throw new OrderBookingTimeException();
        }

    }

    @Nonnull
    private List<LocalDateTime> getValidTimes(RequestOrderDto requestOrderDto) {
        return establishmentServiceClient.getValidTimes(requestOrderDto.getEstablishmentId());
    }
}
