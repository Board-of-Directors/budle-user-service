package ru.nsu.fit.directors.userservice.controller;

import java.util.List;

import javax.annotation.Nonnull;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.directors.userservice.dto.AuthResponse;
import ru.nsu.fit.directors.userservice.dto.MessageDto;
import ru.nsu.fit.directors.userservice.dto.ResponseAuthDto;
import ru.nsu.fit.directors.userservice.dto.request.RequestUserDto;
import ru.nsu.fit.directors.userservice.dto.response.ResponseUserDto;
import ru.nsu.fit.directors.userservice.facade.UserFacade;
import ru.nsu.fit.directors.userservice.security.JwtTokenProvider;
import ru.nsu.fit.directors.userservice.security.JwtTokenRepository;
import ru.nsu.fit.directors.userservice.service.ChatService;
import ru.nsu.fit.directors.userservice.service.SecurityService;

/**
 * Class, that represent user controller.
 * main endpoint = "API:PORT/user"
 */
@RestController
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserFacade userFacade;
    private final SecurityService securityService;
    private final HttpServletRequest httpServletRequest;
    private final ChatService chatService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenRepository jwtTokenRepository;

    /**
     * Post request, that takes user information and add it to our database.
     *
     * @param requestUserDto - information about new user.
     * @return true - if operation was success, false - otherwise
     */
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean register(@RequestBody @Valid RequestUserDto requestUserDto) {
        userFacade.registerUser(requestUserDto);
        securityService.autoLogin(
            requestUserDto.getUsername(),
            requestUserDto.getPassword(),
            httpServletRequest
        );
        return true;
    }

    /**
     * Post request, that authorize user in our system.
     *
     * @param requestUserDto information about user credentials.
     * @return true - if operation was success, false - otherwise.
     */
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean login(@RequestBody RequestUserDto requestUserDto) {
        securityService.autoLogin(
            requestUserDto.getUsername(),
            requestUserDto.getPassword(),
            httpServletRequest
        );
        return true;
    }

    @PostMapping(value = "/login/jwt", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseAuthDto> loginJwt(@RequestBody RequestUserDto userCredentials) {
        return ResponseEntity.ok(createResponseAuthDto(userFacade.loginCredentials(userCredentials)));
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<ResponseAuthDto> refreshJwt(@CookieValue(name = "refreshToken") String refreshToken) {
        return ResponseEntity.ok(createResponseAuthDto(userFacade.refreshToken(refreshToken)));
    }

    @PostMapping(value = "/logout")
    public void logout() {
        securityService.logout(httpServletRequest);
    }

    /**
     * Get request for user information.
     *
     * @return user information
     */
    @GetMapping(value = "/me")
    public ResponseUserDto me() {
        return userFacade.getLoggedInUser();
    }

    @GetMapping("/chat/history")
    public List<MessageDto> getMessages(@RequestParam Long orderId) {
        return chatService.getChat(orderId);
    }

    @Nonnull
    private ResponseAuthDto createResponseAuthDto(AuthResponse authResponse) {
        jwtTokenRepository.setTokenToCookie(authResponse.refreshToken());
        return new ResponseAuthDto(authResponse.accessToken());
    }
}
