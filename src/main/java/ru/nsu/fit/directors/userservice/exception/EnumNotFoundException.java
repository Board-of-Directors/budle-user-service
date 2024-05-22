package ru.nsu.fit.directors.userservice.exception;

public class EnumNotFoundException extends BaseException {
    public <E> EnumNotFoundException(Class<E> enumClass, String name) {
        super("Отсутствует константа %s с названием %s".formatted(enumClass.getSimpleName(), name), "NO_CONSTANT");
    }
}
