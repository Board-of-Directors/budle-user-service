package ru.nsu.fit.directors.userservice.exception;

public class ServerNotAvailableException extends BaseException {
    public ServerNotAvailableException(String message) {
        super("Сервер в данный момент недоступен. Попробуйте позже.", "ServerNotAvailableException");
    }
}
