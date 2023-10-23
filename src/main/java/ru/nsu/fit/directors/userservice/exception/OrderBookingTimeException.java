package ru.nsu.fit.directors.userservice.exception;

public class OrderBookingTimeException extends BaseException {
    public OrderBookingTimeException() {
        super("Время для бронирования выбрано неверно", "OrderBookingTimeException");
    }
}
