package com.VealkeAI.TOlogUseLOG.service;

import com.VealkeAI.TOlogUseLOG.DTO.taskDto.CreateTaskDTO;
import com.VealkeAI.TOlogUseLOG.DTO.taskDto.TaskDTO;
import com.VealkeAI.TOlogUseLOG.DTO.taskDto.TaskSearchFilterDTO;
import com.VealkeAI.TOlogUseLOG.DTO.taskDto.TaskWithPageInfoDTO;
import com.VealkeAI.TOlogUseLOG.web.enums.PriorityStatus;
import com.VealkeAI.TOlogUseLOG.web.enums.State;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {
    TaskDTO createTask(CreateTaskDTO taskToCreate);
    TaskDTO updateTask(Long taskId, TaskDTO taskToUpdate);
    TaskWithPageInfoDTO getTasksByFilter(TaskSearchFilterDTO filter);
    TaskDTO getTaskById(Long id);
    void deleteTask(Long id);
    void changeTaskPriority(Long id, PriorityStatus priority);
    void changeTaskState(Long id, State state);
}
