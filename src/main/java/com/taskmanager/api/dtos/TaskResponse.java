package com.taskmanager.api.dtos;

import com.taskmanager.api.models.enums.TaskStatus;
import java.time.LocalDate;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        LocalDate dueDate,
        String assigneeEmail
) {}