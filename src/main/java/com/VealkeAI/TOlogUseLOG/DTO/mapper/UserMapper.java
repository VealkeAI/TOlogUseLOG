package com.VealkeAI.TOlogUseLOG.DTO.mapper;

import com.VealkeAI.TOlogUseLOG.DTO.user.ObtainedUserDTO;
import com.VealkeAI.TOlogUseLOG.DTO.user.UserDTO;
import com.VealkeAI.TOlogUseLOG.entity.TaskEntity;
import com.VealkeAI.TOlogUseLOG.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserMapper {

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

        var shift = Optional.ofNullable(dto.shiftUTC()).orElse(0);

        return new UserEntity(
                dto.id(),
                dto.tgId(),
                shift
        );
    }

    public UserEntity toEntity(ObtainedUserDTO dto) {

        var entity = new UserEntity();
        var shift = Optional.ofNullable(dto.shiftUTC()).orElse(0);

        entity.setTgId(dto.tgId());
        entity.setShiftUTC(shift);
        entity.setListOfTask(entity.getListOfTask());

        return entity;
    }
}
