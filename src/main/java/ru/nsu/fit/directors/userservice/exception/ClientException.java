package ru.nsu.fit.directors.userservice.exception;

public class ClientException extends BaseException {
    public ClientException(String message) {
        super(message, "ClientException");
    }
}
