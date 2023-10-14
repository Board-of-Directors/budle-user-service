package ru.nsu.fit.directors.userservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUserDto {
    @NotNull(message = "Пароль не может быть пустым")
    @Size(min = 6, message = "Пароль не может быть короче 6 символов")
    private String password;
    @NotNull(message = "Имя пользователя не может быть пустым")
    @Size(min = 2, max = 255, message = "Имя пользователя не может быть короче 2 символов или длиннее 255")
    private String username;
    @NotNull(message = "Номер телефона не может быть пустым")
    @Size(min = 11, message = "Номер телефона не может быть короче 11 символов")
    private String phoneNumber;
}
