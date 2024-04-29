package ru.nsu.fit.directors.userservice.service;

import ru.nsu.fit.directors.userservice.dto.request.RequestOrderDto;
import ru.nsu.fit.directors.userservice.dto.response.ResponseOrderDto;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import java.util.List;

@ParametersAreNonnullByDefault
public interface OrderService {
    /**
     * Создать заказ.
     *
     * @param requestOrderDto информация о заказе.
     */
    void createOrder(RequestOrderDto requestOrderDto);

    /**
     * Отменить заказ.
     *
     * @param orderId идентификатор заказа
     */
    void cancelOrder(Long orderId);

    /**
     * Получить заказы.
     *
     * @param status статус заказа
     * @return список заказов
     */
    @Nonnull
    List<ResponseOrderDto> getOrders(Integer status);

    /**
     * Подтвердить заказ.
     *
     * @param orderId идентификатор заказа
     */
    void confirmOrder(Long orderId);
}
