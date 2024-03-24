package ru.nsu.fit.directors.userservice.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.directors.userservice.dto.MessageDto;
import ru.nsu.fit.directors.userservice.dto.request.ChatMessage;
import ru.nsu.fit.directors.userservice.service.ChatService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user/chat")
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/message")
    public void processMessage(@Payload ChatMessage chatMessage) {
        chatService.save(chatMessage);
    }

    @GetMapping
    public List<MessageDto> getChat(@RequestParam Long orderId) {
        return chatService.getChat(orderId);
    }
}
