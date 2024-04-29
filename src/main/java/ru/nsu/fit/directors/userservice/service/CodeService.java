package ru.nsu.fit.directors.userservice.service;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface CodeService {

    /**
     * Проверить валидность кода.
     *
     * @param phoneNumber номер телефона
     * @param code        код
     * @return является ли код валидным
     */
    boolean checkCode(String phoneNumber, String code);

    /**
     * Сгенерировать код доступа.
     *
     * @param phoneNumber номер телефона
     * @return сгенерирован ли был код
     */
    boolean generateCode(String phoneNumber);
}
