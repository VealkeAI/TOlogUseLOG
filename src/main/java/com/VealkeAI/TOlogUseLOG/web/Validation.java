package com.VealkeAI.TOlogUseLOG.web;

import com.VealkeAI.TOlogUseLOG.DTO.taskDto.CreateTaskDTO;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class Validation {

    public void validateTask(CreateTaskDTO task, Instant currentTime) {
        if(task.deadline() != null && task.deadline().isBefore(currentTime)) {
            throw new IllegalArgumentException("Deadline cannot start in the past");
        }
    }
}
