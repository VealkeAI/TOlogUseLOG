package com.VealkeAI.TOlogUseLOG.controller;

import com.VealkeAI.TOlogUseLOG.DTO.TaskDTO;
import com.VealkeAI.TOlogUseLOG.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/task")
@AllArgsConstructor
public class TaskController {

    private final TaskService service;

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO task) {

        var createdTask = service.createTask(task);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdTask);
    }

    @PutMapping("{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id,
                                              @RequestBody TaskDTO task) {

        var updatedTask = service.updateTask(id, task);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedTask);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<List<TaskDTO>> getAllUserTask(@PathVariable Long id) {

        var taskList = service.getAllUserTask(id);

        return ResponseEntity.status(HttpStatus.OK).body(taskList);
    }

    @GetMapping("{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {

        var task = service.getTaskById(id);

        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {

        service.deleteTask(id);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }
}
