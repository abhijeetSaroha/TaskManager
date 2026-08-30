package com.taskmanager.api.factories;

import com.taskmanager.api.dtos.TaskRequest;
import com.taskmanager.api.dtos.TaskResponse;
import com.taskmanager.api.models.Task;
import com.taskmanager.api.models.User;
import org.springframework.stereotype.Component;

@Component
public class TaskFactory {

    public Task toEntity(TaskRequest request, User assignee) {
        return Task.builder()
                .title(request.title())
                .description(request.description())
                .status(request.status())
                .dueDate(request.dueDate())
                .assignee(assignee)
                .build();
    }

    public TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getDueDate(),
                task.getAssignee() != null ? task.getAssignee().getEmail() : null
        );
    }
}