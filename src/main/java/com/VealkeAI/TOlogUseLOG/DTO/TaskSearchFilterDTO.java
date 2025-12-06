package com.VealkeAI.TOlogUseLOG.DTO;

import com.VealkeAI.TOlogUseLOG.web.enums.PriorityStatus;
import com.VealkeAI.TOlogUseLOG.web.enums.State;

public record TaskSearchFilterDTO(
        String tgId,
        State state,
        PriorityStatus priority,
        Integer pageSize,
        Integer pageNumber
) {
}
