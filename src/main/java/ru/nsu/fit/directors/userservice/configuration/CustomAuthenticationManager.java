package ru.nsu.fit.directors.userservice.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.userservice.exception.WrongCredentialsException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final UserDetails userDetail = userDetailsService.loadUserByUsername(authentication.getName());
        if (!passwordEncoder.matches(authentication.getCredentials().toString(), userDetail.getPassword())) {
            throw new WrongCredentialsException();
        }
        return new UsernamePasswordAuthenticationToken(
            userDetail.getUsername(),
            userDetail.getPassword(),
            userDetail.getAuthorities()
        );
    }

}