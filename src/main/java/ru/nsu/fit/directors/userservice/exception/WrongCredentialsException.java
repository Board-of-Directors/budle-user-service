package ru.nsu.fit.directors.userservice.exception;

public class WrongCredentialsException extends BaseException {
    public WrongCredentialsException() {
        super("Логин или пароль введены неверно", "WrongCredentialsException");
    }
}
