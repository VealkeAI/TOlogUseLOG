package com.VealkeAI.TOlogUseLOG.DTO.user;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ObtainedUserDTO(
        @NotNull
        Long tgId,
        List<Long> taskIdList,
        Integer shiftUTC
) implements User{
}
