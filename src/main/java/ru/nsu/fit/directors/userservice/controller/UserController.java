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
import ru.nsu.fit.directors.userservice.security.JwtTokenRepository;
import ru.nsu.fit.directors.userservice.service.ChatService;
import ru.nsu.fit.directors.userservice.service.SecurityService;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserFacade userFacade;
    private final SecurityService securityService;
    private final HttpServletRequest httpServletRequest;
    private final ChatService chatService;
    private final JwtTokenRepository jwtTokenRepository;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean register(@RequestBody @Valid RequestUserDto requestUserDto) {
        userFacade.registerUser(requestUserDto);
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
