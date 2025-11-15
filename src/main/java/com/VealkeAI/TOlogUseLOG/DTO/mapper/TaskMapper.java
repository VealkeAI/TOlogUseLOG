package com.VealkeAI.TOlogUseLOG.DTO.mapper;

import com.VealkeAI.TOlogUseLOG.DTO.TaskDTO;
import com.VealkeAI.TOlogUseLOG.entity.TaskEntity;
import com.VealkeAI.TOlogUseLOG.repository.TaskRepository;
import com.VealkeAI.TOlogUseLOG.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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

        var user = userRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Not found user by id: " + dto.id()));

        var creationTime = LocalDate.now();

        return new TaskEntity(
                dto.id(),
                user,
                dto.name(),
                dto.description(),
                creationTime,
                dto.deadline(),
                dto.priority(),
                dto.state()
        );
    }
}
