package ru.nsu.fit.directors.userservice.exception;

public class TokenValidationException extends BaseException {
    public TokenValidationException(String message, String type) {
        super(message, type);
    }
}
