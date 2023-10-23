package ru.nsu.fit.directors.userservice.mapper;

import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.userservice.dto.request.RequestOrderDto;
import ru.nsu.fit.directors.userservice.event.OrderCreatedEvent;

@Component
public class OrderMapper {

    public OrderCreatedEvent toEvent(RequestOrderDto order) {
        return new OrderCreatedEvent()
            .setGuestCount(order.getGuestCount())
            .setDate(order.getDate())
            .setTime(order.getTime())
            .setUserId(order.getUserId())
            .setEstablishmentId(order.getEstablishmentId())
            .setSpotId(order.getSpotId());
    }
}
