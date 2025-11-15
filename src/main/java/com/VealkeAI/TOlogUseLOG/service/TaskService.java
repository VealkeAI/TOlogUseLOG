package com.VealkeAI.TOlogUseLOG.service;

import com.VealkeAI.TOlogUseLOG.DTO.TaskDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    TaskDTO createTask(TaskDTO taskToCreate);
    TaskDTO updateTask(Long id, TaskDTO taskToUpdate);
    List<TaskDTO> getAllUserTask(Long userID);
    TaskDTO getTaskById(Long id);
    void deleteTask(Long id);
}
