package ru.nsu.fit.directors.userservice.listener;

import javax.annotation.ParametersAreNonnullByDefault;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.nsu.fit.directors.userservice.event.BusinessMessageEvent;
import ru.nsu.fit.directors.userservice.service.ChatService;

@Slf4j
@Component
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
@KafkaListener(topics = "chatTopic")
public class ChatTopicListener {
    private final ChatService chatService;

    @KafkaHandler
    public void handleMessage(BusinessMessageEvent businessMessageEvent) {
        log.info("Receive business message {}", businessMessageEvent);
        chatService.handleMessage(businessMessageEvent);
    }
}
