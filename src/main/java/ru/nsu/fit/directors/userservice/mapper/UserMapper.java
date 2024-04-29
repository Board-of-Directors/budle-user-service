package ru.nsu.fit.directors.userservice.mapper;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.userservice.dto.request.RequestUserDto;
import ru.nsu.fit.directors.userservice.dto.response.ResponseUserDto;
import ru.nsu.fit.directors.userservice.model.User;

@Component
@RequiredArgsConstructor
public class UserMapper {

    @Nonnull
    public User dtoToModel(RequestUserDto requestUserDto) {
        return new User()
            .setPhoneNumber(requestUserDto.getPhoneNumber())
            .setUsername(requestUserDto.getUsername())
            .setVkUserId(requestUserDto.getVkUserId());
    }

    @Nonnull
    public ResponseUserDto toResponse(User user) {
        return ResponseUserDto.builder()
            .id(user.getId())
            .phoneNumber(user.getPhoneNumber())
            .username(user.getUsername())
            .build();
    }
}
