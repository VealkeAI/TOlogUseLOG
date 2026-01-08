package com.VealkeAI.TOlogUseLOG.service.impl;

import com.VealkeAI.TOlogUseLOG.DTO.taskDto.ObtainedTaskDTO;
import com.VealkeAI.TOlogUseLOG.DTO.taskDto.TaskDTO;
import com.VealkeAI.TOlogUseLOG.DTO.taskDto.TaskSearchFilterDTO;
import com.VealkeAI.TOlogUseLOG.DTO.taskDto.TaskWithPageInfoDTO;
import com.VealkeAI.TOlogUseLOG.DTO.mapper.TaskMapper;
import com.VealkeAI.TOlogUseLOG.repository.TaskRepository;
import com.VealkeAI.TOlogUseLOG.repository.UserRepository;
import com.VealkeAI.TOlogUseLOG.service.TaskService;
import com.VealkeAI.TOlogUseLOG.web.Validation;
import com.VealkeAI.TOlogUseLOG.web.enums.PriorityStatus;
import com.VealkeAI.TOlogUseLOG.web.enums.State;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    private final SchedulerService schedulerService;
    private final Validation validation;

    @Override
    @Transactional
    public TaskDTO createTask(ObtainedTaskDTO taskToCreate) {

        var user = userRepository.findByTgId(taskToCreate.userId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Not found user by telegram id: " + taskToCreate.userId())
                );

        var currentTime = Instant.now().plus(user.getShiftUTC(), ChronoUnit.HOURS);

        validation.validateTask(taskToCreate, currentTime);

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
            schedulerService.createJob(createdTask.getId(), createdTask.getDeadline(), user.getShiftUTC());
        }

        logger.info("Created task with id: {}", createdTask.getId());

        return taskMapper.toDomain(createdTask);
    }

    @Override
    public TaskDTO updateTask(Long taskId, ObtainedTaskDTO taskToUpdate) {

        validation.validateTask(taskToUpdate);

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
    public TaskWithPageInfoDTO getTasksByFilter(TaskSearchFilterDTO filter) {

        int pageSize = filter.pageSize() != null
                ? filter.pageSize()
                : 5;
        int pageNumber = filter.pageNumber() != null
                ? filter.pageNumber()
                : 0;

        var pageable = Pageable
                .ofSize(pageSize)
                .withPage(pageNumber);

        var tgId =  filter.tgId() != null
                ? Long.parseLong(filter.tgId())
                : null;

        var tasks = taskRepository.getTasksByFilter(
                tgId,
                filter.priority(),
                filter.state(),
                pageable
        );

        var taskDtoList = tasks.stream().map(taskMapper::toDomain).toList();

        return new TaskWithPageInfoDTO(
                tasks.getTotalPages(),
                (int) tasks.getTotalElements(),
                taskDtoList
        );
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

    @Override
    @Transactional
    public void changeTaskPriority(Long id, PriorityStatus priority) {

        var task = taskRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Not found task by id: " + id)
                );

        task.setPriority(priority);

        taskRepository.save(task);
    }

    @Override
    @Transactional
    public void changeTaskState(Long id, State state) {

        var task = taskRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Not found task by id: " + id)
                );

        task.setState(state);

        taskRepository.save(task);
    }
}
