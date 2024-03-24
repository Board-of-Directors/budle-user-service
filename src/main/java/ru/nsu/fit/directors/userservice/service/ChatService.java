package ru.nsu.fit.directors.userservice.service;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import ru.nsu.fit.directors.userservice.dto.MessageDto;
import ru.nsu.fit.directors.userservice.dto.request.ChatMessage;

@ParametersAreNonnullByDefault
public interface ChatService {
    /**
     * Сохранить сообщение из чата.
     *
     * @param chatMessage сообщение
     */
    void save(ChatMessage chatMessage);

    /**
     * Получить историю сообщений в рамках брони.
     *
     * @param orderId идентификатор брони
     */
    @Nonnull
    List<MessageDto> getChat(Long orderId);
}
