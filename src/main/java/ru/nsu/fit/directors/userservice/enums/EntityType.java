package ru.nsu.fit.directors.userservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EntityType {
    COMPANY("Заведение"),
    ;

    private final String readableName;
}
