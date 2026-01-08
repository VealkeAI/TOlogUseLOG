package com.VealkeAI.TOlogUseLOG.DTO.taskDto;

import com.VealkeAI.TOlogUseLOG.web.enums.PriorityStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record ObtainedTaskDTO(
        @NotNull
        Long userId,
        @NotNull
        String name,
        String description,
        @FutureOrPresent
        Instant deadline,
        PriorityStatus priority
){
}
