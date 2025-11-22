package com.VealkeAI.TOlogUseLOG.DTO.mapper;

import com.VealkeAI.TOlogUseLOG.DTO.TaskDTO;
import com.VealkeAI.TOlogUseLOG.entity.TaskEntity;
import com.VealkeAI.TOlogUseLOG.repository.UserRepository;
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

        var user = userRepository.findByTgId(dto.userId())
                .orElseThrow(() -> new EntityNotFoundException("Not found user by id: " + dto.userId()));

        var creationTime = Instant.now().plus(user.getShiftUTC(), ChronoUnit.HOURS);

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
