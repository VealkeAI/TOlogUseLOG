package com.VealkeAI.TOlogUseLOG.service;

import com.VealkeAI.TOlogUseLOG.DTO.TaskDTO;
import com.VealkeAI.TOlogUseLOG.DTO.TaskSearchFilterDTO;
import com.VealkeAI.TOlogUseLOG.DTO.TaskWithPageInfoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    TaskDTO createTask(TaskDTO taskToCreate);
    TaskDTO updateTask(Long taskId, TaskDTO taskToUpdate);
    TaskWithPageInfoDTO getTasksByFilter(TaskSearchFilterDTO filter);
    TaskDTO getTaskById(Long id);
    void deleteTask(Long id);
}
