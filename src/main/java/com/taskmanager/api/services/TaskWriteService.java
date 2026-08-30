package com.taskmanager.api.services;

import com.taskmanager.api.dtos.TaskRequest;
import com.taskmanager.api.dtos.TaskResponse;

public interface TaskWriteService {
    TaskResponse createTask(TaskRequest request);
    TaskResponse updateTask(Long id, TaskRequest request);
    void deleteTask(Long id);
    TaskResponse assignTaskToUser(Long taskId, Long userId);
}