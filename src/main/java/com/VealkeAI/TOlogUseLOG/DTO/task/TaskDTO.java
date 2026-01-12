package com.VealkeAI.TOlogUseLOG.DTO.task;

import com.VealkeAI.TOlogUseLOG.web.enums.PriorityStatus;
import com.VealkeAI.TOlogUseLOG.web.enums.State;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.time.Instant;

public record TaskDTO (
        @Null
        Long id,
        @NotNull
        Long userId,
        @NotNull
        String name,
        String description,
        @Null
        Instant creationTime,
        @FutureOrPresent
        Instant deadline,
        PriorityStatus priority,
        State state

) implements Task{
}
