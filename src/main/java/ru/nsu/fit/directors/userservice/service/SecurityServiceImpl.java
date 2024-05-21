package ru.nsu.fit.directors.userservice.service;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.userservice.model.User;
import ru.nsu.fit.directors.userservice.security.JwtTokenRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class SecurityServiceImpl implements SecurityService {
    private final JwtTokenRepository jwtTokenRepository;
    private final UserService userService;

    @Nonnull
    @Override
    public User getLoggedInUser() {
        return userService.getUserById(jwtTokenRepository.getUserIdOrThrow());
    }

    @Override
    public void logout(HttpServletRequest httpServletRequest) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(null);
        SecurityContextHolder.getContext().setAuthentication(null);
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

    }
}
