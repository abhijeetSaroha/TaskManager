package com.taskmanager.api.services;

import com.taskmanager.api.dtos.TaskResponse;
import com.taskmanager.api.models.enums.TaskStatus;
import java.util.List;

public interface TaskReadService {
    List<TaskResponse> getAllTasks();
    TaskResponse getTaskById(Long id);
    List<TaskResponse> getTasksByStatus(TaskStatus status);
}