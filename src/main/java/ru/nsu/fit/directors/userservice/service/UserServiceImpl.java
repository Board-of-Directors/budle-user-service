package ru.nsu.fit.directors.userservice.service;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.userservice.exception.UserAlreadyExistsException;
import ru.nsu.fit.directors.userservice.exception.UserNotFoundException;
import ru.nsu.fit.directors.userservice.model.User;
import ru.nsu.fit.directors.userservice.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final SecurityService securityService;

    @Override
    public void save(User user) {
        log.info("Saving user");
        validate(user);
        userRepository.save(user);
    }

    @Nonnull
    @Override
    public User getLoggedInUser() {
        return securityService.getLoggedInUser();
    }

    @Nonnull
    @Override
    public User getByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(UserNotFoundException::new);
    }

    private void validate(User user) {
        boolean exists = userRepository.existsByPhoneNumber(user.getPhoneNumber())
                         || userRepository.existsByUsername(user.getUsername());
        if (exists) {
            throw new UserAlreadyExistsException();
        }
    }

}
