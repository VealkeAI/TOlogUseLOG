package com.VealkeAI.TOlogUseLOG.DTO;

import com.VealkeAI.TOlogUseLOG.web.PriorityStatus;
import com.VealkeAI.TOlogUseLOG.web.State;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.time.LocalDate;

public record TaskDTO (
        @Null
        Long id,
        @NotNull
        Long userId,
        @NotNull
        String name,
        String description,
        @Null
        LocalDate creationTime,
        @FutureOrPresent
        LocalDate deadline,
        PriorityStatus priority,
        State state

) {
}
