package ru.nsu.fit.directors.userservice.service;

import ru.nsu.fit.directors.userservice.exception.BaseException;

public class OrderManageException extends BaseException {
    public OrderManageException() {
        super("Вы не можете управлять заказом, который вам не принадлежит", "OrderManageException");
    }
}
