package com.VealkeAI.TOlogUseLOG.DTO.mapper;

import com.VealkeAI.TOlogUseLOG.DTO.UserDTO;
import com.VealkeAI.TOlogUseLOG.entity.TaskEntity;
import com.VealkeAI.TOlogUseLOG.entity.UserEntity;
import com.VealkeAI.TOlogUseLOG.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserMapper {

    private final TaskRepository taskRepository;

    public UserDTO toDomain(UserEntity entity) {

        List<Long> taskList = entity.getListOfTask() != null
                ? entity
                .getListOfTask()
                .stream()
                .map(TaskEntity::getId)
                .toList()
                : List.of();

        return new UserDTO(
                entity.getId(),
                entity.getTgId(),
                taskList,
                entity.getShiftUTC()
        );
    }

    public UserEntity toEntity(UserDTO dto) {

        var taskList = Optional
                .ofNullable(dto.taskIdList())
                .map(taskRepository::findAllById)
                .orElse(List.of());

        return new UserEntity(
                dto.id(),
                dto.tgId(),
                taskList,
                dto.shiftUTC()
        );
    }
}
