package ru.nsu.fit.directors.userservice.api;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsu.fit.directors.userservice.dto.MessageDto;
import ru.nsu.fit.directors.userservice.dto.response.BaseResponse;
import ru.nsu.fit.directors.userservice.dto.response.ResponseOrderDto;

@FeignClient("order-service")
public interface OrderServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/order", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse<List<ResponseOrderDto>>> getUserOrders(@RequestParam Long userId);

    @RequestMapping(method = RequestMethod.GET, value = "/order", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse<List<ResponseOrderDto>>> getUserOrders(
        @RequestParam Long userId,
        @RequestParam Integer status
    );

    @RequestMapping(method = RequestMethod.GET, value = "/order/id", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse<ResponseOrderDto>> getOrderById(@RequestParam Long id);

    @RequestMapping(method = RequestMethod.GET, value = "/order/message/user", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse<List<MessageDto>>> getMessages(@RequestParam Long userId, @RequestParam Long orderId);
}
