package ru.nsu.fit.directors.userservice.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(matcher -> matcher.anyRequest().permitAll())
            /*.authorizeHttpRequests(matcher -> matcher
                .requestMatchers("/user/login", "/user/register", "/user/swagger-ui/**", "/user/api-docs/**")
                .permitAll()
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest()
                .authenticated()
            )

             */
            // .cors()
            //.and()
            .build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
        };
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
