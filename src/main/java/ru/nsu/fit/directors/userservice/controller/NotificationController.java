package ru.nsu.fit.directors.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import ru.nsu.fit.directors.userservice.dto.NotificationDto;
import ru.nsu.fit.directors.userservice.service.NotificationService;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping(value = "/user/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationDto> get() {
        return notificationService.getNotifications();
    }

    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<NotificationDto> getFlux() {
        return Flux.zip(Flux.interval(Duration.ofMinutes(1)), notificationService.getFluxNotifications())
            .map(Tuple2::getT2);
    }
}
