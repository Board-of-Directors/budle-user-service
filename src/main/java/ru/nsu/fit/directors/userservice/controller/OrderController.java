package ru.nsu.fit.directors.userservice.controller;

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
import ru.nsu.fit.directors.userservice.dto.response.ResponseOrderDto;
import ru.nsu.fit.directors.userservice.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "user/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    private final OrderService orderService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void order(@RequestBody RequestOrderDto requestOrderDto) {
        orderService.createOrder(requestOrderDto);
    }

    @PutMapping(value = "/cancel")
    public void cancelOrder(@RequestParam Long orderId) {
        orderService.cancelOrder(orderId);
    }

    @GetMapping
    public List<ResponseOrderDto> getOrders(@RequestParam(required = false) Integer status) {
        return orderService.getOrders(status);
    }

    @PutMapping(value = "/confirm")
    public void confirmOrder(@RequestParam Long orderId) {
        orderService.confirmOrder(orderId);
    }
}
