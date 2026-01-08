package com.VealkeAI.TOlogUseLOG.DTO.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.util.List;

public record UserDTO(
        @Null
        Long id,
        @NotNull
        Long tgId,
        List<Long> taskIdList,
        Integer shiftUTC
) {
}
