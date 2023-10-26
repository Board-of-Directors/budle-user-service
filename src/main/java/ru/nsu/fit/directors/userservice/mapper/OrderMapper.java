package ru.nsu.fit.directors.userservice.mapper;

import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.userservice.dto.request.RequestOrderDto;
import ru.nsu.fit.directors.userservice.event.OrderCreatedEvent;
import ru.nsu.fit.directors.userservice.model.User;

@Component
public class OrderMapper {

    public OrderCreatedEvent toEvent(RequestOrderDto order, User user) {
        return new OrderCreatedEvent()
            .setGuestCount(order.getGuestCount())
            .setDate(order.getDate())
            .setTime(order.getTime())
            .setEstablishmentId(order.getEstablishmentId())
            .setSpotId(order.getSpotId())
            .setUserId(user.getId())
            .setGuestName(user.getUsername());
    }
}
