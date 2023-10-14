package ru.nsu.fit.directors.userservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.directors.userservice.dto.request.RequestOrderDto;
import ru.nsu.fit.directors.userservice.dto.request.RequestUserDto;
import ru.nsu.fit.directors.userservice.dto.response.ResponseOrderDto;
import ru.nsu.fit.directors.userservice.model.User;
import ru.nsu.fit.directors.userservice.service.SecurityService;
import ru.nsu.fit.directors.userservice.service.UserService;

import java.util.List;

/**
 * Class, that represent user controller.
 * main endpoint = "API:PORT/user"
 */
@RestController
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@RequestMapping(value = "user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserService userService;
    private final SecurityService securityService;
    private final HttpServletRequest httpServletRequest;

    /**
     * Post request, that takes user information and add it to our database.
     *
     * @param requestUserDto - information about new user.
     * @return true - if operation was success, false - otherwise
     */
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean register(@RequestBody @Valid RequestUserDto requestUserDto) {
        userService.registerUser(requestUserDto);
        securityService.autoLogin(
            requestUserDto.getUsername(),
            requestUserDto.getPassword(),
            httpServletRequest
        );
        return true;
    }

    /**
     * Post request, that authorize user in our system.
     *
     * @param requestUserDto information about user credentials.
     * @return true - if operation was success, false - otherwise.
     */
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean login(@RequestBody RequestUserDto requestUserDto) {
        securityService.autoLogin(
            requestUserDto.getUsername(),
            requestUserDto.getPassword(),
            httpServletRequest
        );
        return true;
    }

    /**
     * Post request for creating a booking order.
     *
     * @param requestOrderDto request for creating order
     */

    @PostMapping(value = "/order/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void order(@RequestBody RequestOrderDto requestOrderDto) {
        userService.createOrder(requestOrderDto);
    }

    /**
     * Put request for cancelling order.
     *
     * @param orderId identifier of order to cancel it.
     */
    @PutMapping(value = "/order/cancel", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void cancelOrder(@RequestParam Long orderId) {
        userService.cancelOrder(orderId);
    }

    /**
     * Get request for getting all user orders.
     *
     * @param status order status (optional)
     * @return list of user orders
     */

    @GetMapping(value = "/order", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseOrderDto> getOrders(@RequestParam(required = false) Integer status) {
        return userService.getOrders(status);
    }

    /**
     * Get request for user information.
     *
     * @return user information
     */
    @GetMapping(value = "/me")
    public User me() {
        return securityService.getLoggedInUser();
    }

}
