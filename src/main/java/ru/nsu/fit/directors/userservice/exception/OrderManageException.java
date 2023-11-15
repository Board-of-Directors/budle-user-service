package ru.nsu.fit.directors.userservice.exception;

public class OrderManageException extends BaseException {
    public OrderManageException() {
        super("Вы не можете управлять заказом, который вам не принадлежит", "OrderManageException");
    }
}
