package ru.nsu.fit.directors.userservice.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.userservice.dto.request.RequestUserDto;
import ru.nsu.fit.directors.userservice.model.User;

/**
 * Class, that represent mapper for user class.
 */
@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;

    /**
     * Convert user dto object to user model object.
     *
     * @param requestUserDto object.
     * @return user model object.
     */

    public User dtoToModel(RequestUserDto requestUserDto) {
        return modelMapper.map(requestUserDto, User.class);
    }
}
