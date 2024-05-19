package ru.nsu.fit.directors.userservice.exception;

import ru.nsu.fit.directors.userservice.enums.EntityType;

public class EntityNotFoundException extends BaseException {
    public EntityNotFoundException(EntityType entityType, Long id) {
        super("%s с идентификатором %s не найдено".formatted(entityType.getReadableName(), id), "NotFoundException");
    }
}
