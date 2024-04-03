package ru.nsu.fit.directors.userservice.service;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import ru.nsu.fit.directors.userservice.dto.MessageDto;
import ru.nsu.fit.directors.userservice.dto.request.ChatMessage;
import ru.nsu.fit.directors.userservice.event.BusinessMessageEvent;

@ParametersAreNonnullByDefault
public interface ChatService {
    /**
     * Сохранить сообщение из чата.
     *
     * @param chatMessage сообщение
     */
    void save(ChatMessage chatMessage, Long orderId);

    /**
     * Получить историю сообщений в рамках брони.
     *
     * @param orderId идентификатор брони
     */
    @Nonnull
    List<MessageDto> getChat(Long orderId);

    /**
     * Обработать входящее сообщение
     *
     * @param businessMessageEvent входящее сообщение
     */
    void handleMessage(BusinessMessageEvent businessMessageEvent);
}
