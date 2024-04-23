package ru.nsu.fit.directors.userservice.exception;

public class ServerNotAvailableException extends BaseException {
    public ServerNotAvailableException() {
        super("Сервер в данный момент недоступен. Попробуйте позже.", "ServerNotAvailableException");
    }
}
