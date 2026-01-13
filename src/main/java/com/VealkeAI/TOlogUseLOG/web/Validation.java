package com.VealkeAI.TOlogUseLOG.web;

import com.VealkeAI.TOlogUseLOG.DTO.task.Task;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class Validation {

    public <T extends Task> void validateTask(T task, Instant currentTime) {
        if (task.name() == null || task.name().isBlank()) {
            throw new IllegalArgumentException("Task name cannot be empty");
        }
        if (task.deadline() != null && task.deadline().isBefore(currentTime)) {
            throw new IllegalArgumentException("Deadline cannot start in the past");
        }
    }
}
