package ru.nsu.fit.directors.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.userservice.dto.request.RequestUserDto;
import ru.nsu.fit.directors.userservice.dto.response.ResponseUserDto;
import ru.nsu.fit.directors.userservice.exception.UserAlreadyExistsException;
import ru.nsu.fit.directors.userservice.mapper.UserMapper;
import ru.nsu.fit.directors.userservice.model.User;
import ru.nsu.fit.directors.userservice.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final SecurityService securityService;

    @Override
    public void registerUser(RequestUserDto requestUserDto) {
        log.info("Registering user");
        if (existsByPhoneNumberOrUsername(requestUserDto)) {
            throw new UserAlreadyExistsException();
        } else {
            User user = userMapper.dtoToModel(requestUserDto);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }

    @Override
    public ResponseUserDto getLoggedInUser() {
        User user = securityService.getLoggedInUser();
        return ResponseUserDto.builder()
            .id(user.getId())
            .phoneNumber(user.getPhoneNumber())
            .username(user.getUsername())
            .build();
    }

    private boolean existsByPhoneNumberOrUsername(RequestUserDto userDto) {
        return userRepository.existsByPhoneNumber(userDto.getPhoneNumber())
            || userRepository.existsByUsername(userDto.getUsername());
    }


}
