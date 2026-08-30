package com.taskmanager.api.controllers;

import com.taskmanager.api.dtos.TaskRequest;
import com.taskmanager.api.dtos.TaskResponse;
import com.taskmanager.api.facades.AiServiceFacade;
import com.taskmanager.api.models.enums.TaskStatus;
import com.taskmanager.api.services.TaskReadService;
import com.taskmanager.api.services.TaskWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskReadService taskReadService;
    private final TaskWriteService taskWriteService;
    private final AiServiceFacade aiServiceFacade;

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return ResponseEntity.ok(taskReadService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(taskReadService.getTaskById(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskResponse>> getTasksByStatus(@PathVariable("status") TaskStatus status) {
        return ResponseEntity.ok(taskReadService.getTasksByStatus(status));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest request) {
        return new ResponseEntity<>(taskWriteService.createTask(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable("id") Long id,
            @RequestBody TaskRequest request) {
        return ResponseEntity.ok(taskWriteService.updateTask(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id) {
        taskWriteService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<TaskResponse> assignTask(
            @PathVariable("taskId") Long taskId,
            @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(taskWriteService.assignTaskToUser(taskId, userId));
    }

    @GetMapping("/{id}/summary")
    public ResponseEntity<String> getTaskSummary(@PathVariable("id") Long id) {
        TaskResponse task = taskReadService.getTaskById(id);
        String prompt = String.format("Summarize the following task. Title: %s. Description: %s",
                task.title(), task.description());
        String aiSummary = aiServiceFacade.generateTaskSummary(prompt);
        return ResponseEntity.ok(aiSummary);
    }
}