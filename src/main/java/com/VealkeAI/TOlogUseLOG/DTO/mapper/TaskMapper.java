package com.VealkeAI.TOlogUseLOG.DTO.mapper;

import com.VealkeAI.TOlogUseLOG.DTO.taskDto.ObtainedTaskDTO;
import com.VealkeAI.TOlogUseLOG.DTO.taskDto.TaskDTO;
import com.VealkeAI.TOlogUseLOG.entity.TaskEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TaskMapper {

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

    public TaskEntity toEntity(ObtainedTaskDTO dto) {

        var taskEntity = new TaskEntity();
        taskEntity.setName(dto.name());
        taskEntity.setDescription(dto.description());
        taskEntity.setDeadline(dto.deadline());
        taskEntity.setPriority(dto.priority());

        return taskEntity;
    }
}
