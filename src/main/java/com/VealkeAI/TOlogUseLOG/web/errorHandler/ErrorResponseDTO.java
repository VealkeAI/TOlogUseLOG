package com.VealkeAI.TOlogUseLOG.web.errorHandler;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
        String message,
        String detailMessage,
        LocalDateTime errorTime
) {
}
