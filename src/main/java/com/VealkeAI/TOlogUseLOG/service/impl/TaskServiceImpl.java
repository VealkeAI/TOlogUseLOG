package com.VealkeAI.TOlogUseLOG.service.impl;

import com.VealkeAI.TOlogUseLOG.DTO.TaskDTO;
import com.VealkeAI.TOlogUseLOG.DTO.TaskSearchFilterDTO;
import com.VealkeAI.TOlogUseLOG.DTO.mapper.TaskMapper;
import com.VealkeAI.TOlogUseLOG.repository.TaskRepository;
import com.VealkeAI.TOlogUseLOG.repository.UserRepository;
import com.VealkeAI.TOlogUseLOG.service.TaskService;
import com.VealkeAI.TOlogUseLOG.web.enums.PriorityStatus;
import com.VealkeAI.TOlogUseLOG.web.enums.State;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.jobrunr.scheduling.ScheduleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    private final SchedulerService schedulerService;

    @Override
    public TaskDTO createTask(TaskDTO taskToCreate) {

        if(taskToCreate.id() != null || taskToCreate.creationTime() != null) {
            throw new IllegalArgumentException("ID and creation time should be empty");
        }

        var user = userRepository.findByTgId(taskToCreate.userId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Not found user by telegram id: " + taskToCreate.userId())
                );

        var currentTime = Instant.now().plus(user.getShiftUTC(), ChronoUnit.HOURS);


        if(
                taskToCreate.deadline() != null &&
                taskToCreate
                        .deadline()
                        .isBefore(currentTime))
        {
            throw new IllegalArgumentException("Deadline cannot start in the past");
        }

        var taskToSave = taskMapper.toEntity(taskToCreate);

        taskToSave.setUser(user);
        taskToSave.setCreationTime(currentTime);
        taskToSave.setPriority(taskToSave.getPriority() != null
                    ? taskToSave.getPriority()
                    : PriorityStatus.DEFAULT);
        taskToSave.setState(taskToSave.getState() != null
                ? taskToSave.getState()
                : State.DO);

        var createdTask = taskRepository.save(taskToSave);

        if (createdTask.getDeadline() != null) {
            try {
                 schedulerService.createJob(createdTask.getId(), createdTask.getDeadline(), user.getShiftUTC());
            } catch (ScheduleException e) {
                logger.error("failed to create task: {}", e.getMessage());
                //TODO: в случае ошибки отправить запрос на сервер с предупреждением
            }
        }

        return taskMapper.toDomain(createdTask);
    }

    @Override
    public TaskDTO updateTask(Long taskId, TaskDTO taskToUpdate) {

        if(taskToUpdate.id() != null || taskToUpdate.creationTime() != null) {
            throw new IllegalArgumentException("ID and creation time should be empty");
        }

        if(taskToUpdate.deadline() != null && taskToUpdate.deadline().isBefore(Instant.now())){
            throw new IllegalArgumentException("Deadline cannot start in the past");
        }

        var oldTask = taskRepository.findById(taskId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Not found task by id: " + taskId)
                );

        var taskToSave = taskMapper.toEntity(taskToUpdate);
        taskToSave.setId(taskId);
        taskToSave.setUser(oldTask.getUser());
        taskToSave.setCreationTime(oldTask.getCreationTime());

        var updatedTask = taskRepository.save(taskToSave);

        return taskMapper.toDomain(updatedTask);
    }

    @Override
    public List<TaskDTO> getTasksByFilter(TaskSearchFilterDTO filter) {

        int pageSize = filter.pageSize() != null
                ? filter.pageSize()
                : 5;
        int pageNumber = filter.pageNumber() != null
                ? filter.pageNumber()
                : 0;

        var pageable = Pageable
                .ofSize(pageSize)
                .withPage(pageNumber);

        var tasks = taskRepository.getTasksByFilter(
                Long.parseLong(filter.tgId()),
                filter.priority(),
                filter.state(),
                pageable
        );

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
