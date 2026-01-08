package com.VealkeAI.TOlogUseLOG.DTO.taskDto;

import jakarta.validation.constraints.Null;

import java.util.List;

public record TaskWithPageInfoDTO (
        @Null
        Integer totalPages,
        @Null
        Integer totalElements,
        @Null
        List<TaskDTO> content
){
}
