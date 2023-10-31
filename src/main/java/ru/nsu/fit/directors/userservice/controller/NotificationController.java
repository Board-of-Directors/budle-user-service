package ru.nsu.fit.directors.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.directors.userservice.service.NotificationDto;
import ru.nsu.fit.directors.userservice.service.NotificationService;

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
}
