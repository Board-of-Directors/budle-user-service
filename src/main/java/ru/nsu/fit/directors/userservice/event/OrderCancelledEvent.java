package ru.nsu.fit.directors.userservice.event;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class OrderCancelledEvent implements OrderEvent {
    private Long orderId;
}
