package ru.nsu.fit.directors.userservice.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.userservice.exception.UserNotFoundException;
import ru.nsu.fit.directors.userservice.model.User;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityServiceImpl implements SecurityService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Override
    public User getLoggedInUser() {
        Object info = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (info instanceof User user) {
            log.info(user.getUsername());
            return user;
        }
        log.warn("Info is not instance of user details");
        throw new BadRequestException("User not logged in");
    }

    @Override
    public void autoLogin(String username, String password, HttpServletRequest request) {
        log.info("Auto login for username {} and password {}", username, password);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            log.warn("User details was null");
            throw new UserNotFoundException();
        }
        log.info("Auto login success for {}", userDetails);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(
                userDetails,
                password,
                userDetails.getAuthorities()
            );

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        }
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
