package com.VealkeAI.TOlogUseLOG.DTO.task;

import com.VealkeAI.TOlogUseLOG.web.enums.PriorityStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record UpdateTaskDTO (
        @NotNull
        String name,
        String description,
        @FutureOrPresent
        Instant deadline,
        PriorityStatus priority
) implements Task{
}
