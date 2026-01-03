package com.VealkeAI.TOlogUseLOG.controller;

import com.VealkeAI.TOlogUseLOG.DTO.TaskDTO;
import com.VealkeAI.TOlogUseLOG.DTO.TaskSearchFilterDTO;
import com.VealkeAI.TOlogUseLOG.DTO.TaskWithPageInfoDTO;
import com.VealkeAI.TOlogUseLOG.service.TaskService;
import com.VealkeAI.TOlogUseLOG.web.enums.PriorityStatus;
import com.VealkeAI.TOlogUseLOG.web.enums.State;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("api/v1/task")
@AllArgsConstructor
@Tag(name = "Task API")
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

    @GetMapping
    public ResponseEntity<TaskWithPageInfoDTO> getTasksAllTasks(@RequestParam(name = "tgId", required = false) String tgId,
                                                                @RequestParam(name = "state", required = false) State state,
                                                                @RequestParam(name = "priority", required = false) PriorityStatus priority,
                                                                @RequestParam(name = "pageSize", required = false) Integer pageSize,
                                                                @RequestParam(name = "pageNumber", required = false) Integer pageNumber) {

        var filter = new TaskSearchFilterDTO(tgId, state, priority, pageSize, pageNumber);

        var taskList = service.getTasksByFilter(filter);

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

    @PatchMapping("priority/{id}/{priority}")
    public ResponseEntity<Void> updateTaskPriority(@PathVariable Long id,
                                                   @PathVariable PriorityStatus priority) {

        service.changeTaskPriority(id, priority);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PatchMapping("state/{id}/{state}")
    public ResponseEntity<Void> updateTaskState(@PathVariable Long id,
                                                @PathVariable State state) {
        service.changeTaskState(id, state);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
