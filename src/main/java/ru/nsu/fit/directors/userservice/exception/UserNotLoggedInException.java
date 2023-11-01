package ru.nsu.fit.directors.userservice.exception;

public class UserNotLoggedInException extends BaseException {
    public UserNotLoggedInException() {
        super("Пользователь не вошел в систему", "UserNotLoggedInException");
    }
}
