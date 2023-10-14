package ru.nsu.fit.directors.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.nsu.fit.directors.userservice.dto.request.RequestOrderDto;
import ru.nsu.fit.directors.userservice.dto.request.RequestUserDto;
import ru.nsu.fit.directors.userservice.dto.response.ResponseOrderDto;
import ru.nsu.fit.directors.userservice.event.OrderCancelledEvent;
import ru.nsu.fit.directors.userservice.event.OrderCreatedEvent;
import ru.nsu.fit.directors.userservice.event.OrderEvent;
import ru.nsu.fit.directors.userservice.exception.UserAlreadyExistsException;
import ru.nsu.fit.directors.userservice.mapper.UserMapper;
import ru.nsu.fit.directors.userservice.model.User;
import ru.nsu.fit.directors.userservice.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final WebClient.Builder establishmentClientBuilder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    private final SecurityService securityService;
    private final WebClient.Builder orderClientBuilder;

    @Override
    public void registerUser(RequestUserDto requestUserDto) {
        log.info("Registering user");
        if (existsByPhoneNumberOrUsername(requestUserDto)) {
            log.warn("User already exists");
            throw new UserAlreadyExistsException();
        } else {
            User user = userMapper.dtoToModel(requestUserDto);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            log.info("Registered user");
        }
    }

    private boolean existsByPhoneNumberOrUsername(RequestUserDto userDto) {
        return userRepository.existsByPhoneNumber(userDto.getPhoneNumber())
            || userRepository.existsByUsername(userDto.getUsername());
    }

    @Override
    public void createOrder(RequestOrderDto order) {
        User loggedUser = securityService.getLoggedInUser();
        order.setUserId(loggedUser.getId());
        validateOrderTime(order);
        kafkaTemplate.send("orderTopic", toEvent(order));
    }

    private OrderEvent toEvent(RequestOrderDto order) {
        return new OrderCreatedEvent()
            .setGuestCount(order.getGuestCount())
            .setDate(order.getDate())
            .setTime(order.getTime())
            .setUserId(order.getUserId())
            .setEstablishmentId(order.getEstablishmentId())
            .setSpotId(order.getSpotId());
    }

    @Override
    public void cancelOrder(Long orderId) {
        validateUserOrder(orderId);
        kafkaTemplate.send("orderTopic", new OrderCancelledEvent()
            .setOrderId(orderId));
    }

    @Override
    public List<ResponseOrderDto> getOrders(Integer status) {
        ParameterizedTypeReference<List<ResponseOrderDto>> ref = new ParameterizedTypeReference<>() {
        };
        User loggedUser = securityService.getLoggedInUser();
        return Optional.ofNullable(orderClientBuilder.build()
            .get()
            .uri(uriBuilder -> uriBuilder.path("/order")
                .queryParam("userId", loggedUser.getId())
                .queryParam("status", status)
                .build())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntity(ref)
            .block()).map(HttpEntity::getBody).orElse(null);

    }

    private void validateUserOrder(Long orderId) {
        var orders = getOrders(0);
        if (orders.stream()
            .map(ResponseOrderDto::getId)
            .noneMatch(id -> id.equals(orderId))) {
            throw new RuntimeException("You cannot do this");

        }

    }

    private void validateOrderTime(RequestOrderDto requestOrderDto) {
        ParameterizedTypeReference<List<LocalDateTime>> ref = new ParameterizedTypeReference<>() {
        };
        boolean isOrderTimeNotValid = Optional.ofNullable(establishmentClientBuilder.build()
                .get()
                .uri(uriBuilder -> uriBuilder.path("/internal/time")
                    .queryParam("establishmentId", requestOrderDto.getEstablishmentId())
                    .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(ref)
                .block())
            .map(HttpEntity::getBody)
            .map(times -> !times.contains(requestOrderDto.getDate().atTime(requestOrderDto.getTime())))
            .orElse(true);
        if (isOrderTimeNotValid) {
            throw new RuntimeException("Not valid time");

        }

    }

}
