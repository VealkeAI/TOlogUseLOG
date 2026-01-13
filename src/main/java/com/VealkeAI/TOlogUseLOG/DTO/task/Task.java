package com.VealkeAI.TOlogUseLOG.DTO.task;


import com.VealkeAI.TOlogUseLOG.web.enums.PriorityStatus;

import java.time.Instant;

public interface Task {
    String name();
    String description();
    Instant deadline();
    PriorityStatus priority();
}
