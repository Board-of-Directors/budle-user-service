package ru.nsu.fit.directors.userservice.service;

import ru.nsu.fit.directors.userservice.dto.request.RequestOrderDto;
import ru.nsu.fit.directors.userservice.dto.response.ResponseOrderDto;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public interface OrderService {
    void createOrder(RequestOrderDto requestOrderDto);

    void cancelOrder(Long orderId);

    List<ResponseOrderDto> getOrders(Integer status);

    void confirmOrder(Long orderId);
}
