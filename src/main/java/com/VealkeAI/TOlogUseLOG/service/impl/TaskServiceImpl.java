package com.VealkeAI.TOlogUseLOG.service.impl;

import com.VealkeAI.TOlogUseLOG.DTO.TaskDTO;
import com.VealkeAI.TOlogUseLOG.DTO.mapper.TaskMapper;
import com.VealkeAI.TOlogUseLOG.repository.TaskRepository;
import com.VealkeAI.TOlogUseLOG.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final SchedulerService schedulerService;

    @Override
    public TaskDTO createTask(TaskDTO taskToCreate) {

        var shift = taskRepository.getUserShiftUTC(taskToCreate.userId())
                .orElse(0);

        if(taskToCreate.id() != null || taskToCreate.creationTime() != null) {
            throw new IllegalArgumentException("ID and creation time should be empty");
        }

        if(taskToCreate.deadline() != null && taskToCreate.deadline().isBefore(Instant.now().plus(shift, ChronoUnit.HOURS))) {
            throw new IllegalArgumentException("Deadline cannot start in the past");
        }

        var createdTask = taskRepository.save(taskMapper.toEntity(taskToCreate));
        schedulerService.createJob(createdTask, shift);

        return taskMapper.toDomain(createdTask);
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskToUpdate) {

        if(taskToUpdate.id() != null || taskToUpdate.creationTime() != null) {
            throw new IllegalArgumentException("ID and creation time should be empty");
        }

        if(taskToUpdate.deadline() != null && taskToUpdate.deadline().isBefore(Instant.now())){
            throw new IllegalArgumentException("Deadline cannot start in the past");
        }

        taskRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Not found task by id: " + id)
                );

        var userToSave = taskMapper.toEntity(taskToUpdate);
        userToSave.setId(id);

        var updatedTask = taskRepository.save(userToSave);

        return taskMapper.toDomain(updatedTask);
    }

    @Override
    public List<TaskDTO> getAllUserTask(Long userId) {

        var tasks = taskRepository.getAllUserTask(userId);

        return tasks.stream().map(taskMapper::toDomain).toList();
    }

    @Override
    public TaskDTO getTaskById(Long id) {

        var task = taskRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Not found task by id: " + id)
                );

        return taskMapper.toDomain(task);
    }

    @Override
    public void deleteTask(Long id) {

        var userToDelete = taskRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Not found task by id: " + id)
                );

        taskRepository.delete(userToDelete);
    }
}
