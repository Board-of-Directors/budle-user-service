package ru.nsu.fit.directors.userservice.facade;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.userservice.dto.request.RequestUserDto;
import ru.nsu.fit.directors.userservice.dto.response.ResponseUserDto;
import ru.nsu.fit.directors.userservice.mapper.UserMapper;
import ru.nsu.fit.directors.userservice.model.User;
import ru.nsu.fit.directors.userservice.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class UserFacadeImpl implements UserFacade {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Nonnull
    @Override
    public ResponseUserDto getLoggedInUser() {
        User user = userService.getLoggedInUser();
        return ResponseUserDto.builder()
            .id(user.getId())
            .phoneNumber(user.getPhoneNumber())
            .username(user.getUsername())
            .build();
    }

    @Override
    public void registerUser(RequestUserDto requestUserDto) {
        log.info("Registering user with");
        User user = userMapper.dtoToModel(requestUserDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
    }
}
