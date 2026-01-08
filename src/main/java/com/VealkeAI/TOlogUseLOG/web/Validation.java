package com.VealkeAI.TOlogUseLOG.web;

import com.VealkeAI.TOlogUseLOG.DTO.task.ObtainedTaskDTO;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class Validation {

    public void validateTask(ObtainedTaskDTO task, Instant currentTime) {
        if(task.deadline() != null && task.deadline().isBefore(currentTime)) {
            throw new IllegalArgumentException("Deadline cannot start in the past");
        }
    }

    public void validateTask(ObtainedTaskDTO task) {
        if (task.deadline() != null && task.deadline().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Deadline cannot start in the past");
        }
    }
}
