package com.VealkeAI.TOlogUseLOG.DTO.mapper;

import com.VealkeAI.TOlogUseLOG.DTO.TaskDTO;
import com.VealkeAI.TOlogUseLOG.entity.TaskEntity;
import com.VealkeAI.TOlogUseLOG.repository.UserRepository;
import com.VealkeAI.TOlogUseLOG.web.enums.PriorityStatus;
import com.VealkeAI.TOlogUseLOG.web.enums.State;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@AllArgsConstructor
public class TaskMapper {

    private final UserRepository userRepository;

    public TaskDTO toDomain(TaskEntity entity) {
        return new TaskDTO(
                entity.getId(),
                entity.getUser().getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCreationTime(),
                entity.getDeadline(),
                entity.getPriority(),
                entity.getState()
        );
    }

    public TaskEntity toEntity(TaskDTO dto) {
        return new TaskEntity(
                dto.id(),
                dto.name(),
                dto.description(),
                dto.deadline(),
                dto.priority(),
                dto.state()
        );
    }
}
