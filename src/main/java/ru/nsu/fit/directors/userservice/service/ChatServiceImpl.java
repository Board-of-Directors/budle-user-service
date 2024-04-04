package ru.nsu.fit.directors.userservice.service;

import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.userservice.api.OrderServiceClient;
import ru.nsu.fit.directors.userservice.dto.MessageDto;
import ru.nsu.fit.directors.userservice.dto.request.ChatMessage;
import ru.nsu.fit.directors.userservice.event.BusinessMessageEvent;
import ru.nsu.fit.directors.userservice.event.UserMessageEvent;
import ru.nsu.fit.directors.userservice.exception.UserNotFoundException;
import ru.nsu.fit.directors.userservice.model.User;
import ru.nsu.fit.directors.userservice.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class ChatServiceImpl implements ChatService {
    private static final String ORDER_TOPIC = "orderTopic";
    private static final String CHAT_TOPIC = "user-chat-topic";

    private final KafkaTemplate<String, UserMessageEvent> kafkaTemplate;
    private final SecurityService securityService;
    private final OrderServiceClient orderServiceClient;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserRepository userRepository;

    @Override
    public void save(ChatMessage chatMessage, Long orderId) {
        User user = userRepository.findById(chatMessage.userId()).orElseThrow(UserNotFoundException::new);
        kafkaTemplate.send(
            ORDER_TOPIC,
            new UserMessageEvent(user.getId(), orderId, chatMessage.message())
        );
        kafkaTemplate.send(
            CHAT_TOPIC,
            new UserMessageEvent(user.getId(), orderId, chatMessage.message())
        );
        log.info("Successfully sent to CHAT_TOPIC {}", chatMessage);
    }

    @Nonnull
    @Override
    public List<MessageDto> getChat(Long orderId) {
        User user = securityService.getLoggedInUser();
        return Objects.requireNonNull(orderServiceClient.getMessages(user.getId(), orderId).getBody()).getResult();
    }

    @Override
    public void handleMessage(BusinessMessageEvent businessMessageEvent) {
        simpMessagingTemplate.convertAndSend("/topic/" + businessMessageEvent.orderId(), businessMessageEvent);
    }
}
