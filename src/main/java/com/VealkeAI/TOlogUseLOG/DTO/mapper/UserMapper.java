package com.VealkeAI.TOlogUseLOG.DTO.mapper;

import com.VealkeAI.TOlogUseLOG.DTO.UserDTO;
import com.VealkeAI.TOlogUseLOG.entity.TaskEntity;
import com.VealkeAI.TOlogUseLOG.entity.UserEntity;
import com.VealkeAI.TOlogUseLOG.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {

    private final TaskRepository taskRepository;

    public UserDTO toDomain(UserEntity entity) {

        var taskList = entity.getListOfTask() != null
                ? entity
                .getListOfTask()
                .stream()
                .map(TaskEntity::getId)
                .toList()
                : null;

        return new UserDTO(
                entity.getId(),
                entity.getTgId(),
                taskList
        );
    }

    public UserEntity toEntity(UserDTO dto) {

        var taskList = dto.taskIdList() != null
                ? dto
                .taskIdList()
                .stream()
                .map(id -> taskRepository.findById(id)
                        .orElse(null)
                ).toList()
                : null;

        return new UserEntity(
                dto.Id(),
                dto.tgId(),
                taskList
        );
    }
}
