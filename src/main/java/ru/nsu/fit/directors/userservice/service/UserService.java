package ru.nsu.fit.directors.userservice.service;


import ru.nsu.fit.directors.userservice.dto.request.RequestOrderDto;
import ru.nsu.fit.directors.userservice.dto.request.RequestUserDto;
import ru.nsu.fit.directors.userservice.dto.response.ResponseOrderDto;

import java.util.List;

/**
 * Service that responsible for users.
 */
public interface UserService {

    /**
     * Function that register new user in our system.
     *
     * @param requestUserDto provide information about new user.
     */
    void registerUser(RequestUserDto requestUserDto);

    void createOrder(RequestOrderDto requestOrderDto);

    void cancelOrder(Long orderId);

    List<ResponseOrderDto> getOrders(Integer status);
}
