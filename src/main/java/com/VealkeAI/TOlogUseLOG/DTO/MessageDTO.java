package com.VealkeAI.TOlogUseLOG.DTO;

import jakarta.validation.constraints.NotNull;

public record MessageDTO(
        @NotNull
        String id,
        @NotNull
        String tgId,
        @NotNull
        String name,
        @NotNull
        String description
) {
}
