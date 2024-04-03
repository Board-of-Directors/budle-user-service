package ru.nsu.fit.directors.userservice.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsu.fit.directors.userservice.dto.MessageDto;
import ru.nsu.fit.directors.userservice.dto.request.ChatMessage;
import ru.nsu.fit.directors.userservice.service.ChatService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/send/{orderId}")
    public void send(@DestinationVariable Long orderId, Message<ChatMessage> message) {
        log.info("received order message {}", orderId);
        chatService.save(message.getPayload(), orderId);
    }

    @GetMapping("/user/chat/history")
    public List<MessageDto> getMessages(@RequestParam Long orderId) {
        return chatService.getChat(orderId);
    }
}
