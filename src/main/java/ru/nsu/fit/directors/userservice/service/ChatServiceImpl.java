package ru.nsu.fit.directors.userservice.service;

import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.nsu.fit.directors.userservice.api.OrderServiceClient;
import ru.nsu.fit.directors.userservice.dto.MessageDto;
import ru.nsu.fit.directors.userservice.dto.request.ChatMessage;
import ru.nsu.fit.directors.userservice.event.UserMessageEvent;
import ru.nsu.fit.directors.userservice.model.User;

@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class ChatServiceImpl implements ChatService {
    private static final String CHAT_TOPIC = "chatTopic";
    private final KafkaTemplate<String, UserMessageEvent> kafkaTemplate;
    private final SecurityService securityService;
    private final OrderServiceClient orderServiceClient;

    @Override
    public void save(ChatMessage chatMessage) {
        User user = securityService.getLoggedInUser();
        kafkaTemplate.send(
            CHAT_TOPIC,
            new UserMessageEvent(user.getId(), chatMessage.orderId(), chatMessage.message())
        );
    }

    @Nonnull
    @Override
    public List<MessageDto> getChat(Long orderId) {
        User user = securityService.getLoggedInUser();
        return Objects.requireNonNull(orderServiceClient.getMessages(user.getId(), orderId).getBody()).getResult();
    }
}
