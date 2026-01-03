package com.VealkeAI.TOlogUseLOG.service;

import com.VealkeAI.TOlogUseLOG.DTO.TaskDTO;
import com.VealkeAI.TOlogUseLOG.DTO.TaskSearchFilterDTO;
import com.VealkeAI.TOlogUseLOG.DTO.TaskWithPageInfoDTO;
import com.VealkeAI.TOlogUseLOG.web.enums.PriorityStatus;
import com.VealkeAI.TOlogUseLOG.web.enums.State;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {
    TaskDTO createTask(TaskDTO taskToCreate);
    TaskDTO updateTask(Long taskId, TaskDTO taskToUpdate);
    TaskWithPageInfoDTO getTasksByFilter(TaskSearchFilterDTO filter);
    TaskDTO getTaskById(Long id);
    void deleteTask(Long id);
    void changeTaskPriority(Long id, PriorityStatus priority);
    void changeTaskState(Long id, State state);
}
