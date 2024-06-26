package ru.nsu.fit.directors.userservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import ru.nsu.fit.directors.userservice.dto.request.ChatMessage;
import ru.nsu.fit.directors.userservice.service.ChatService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/send/{orderId}")
    public void send(@DestinationVariable Long orderId, Message<ChatMessage> message) {
        chatService.save(message.getPayload(), orderId);
    }
}
