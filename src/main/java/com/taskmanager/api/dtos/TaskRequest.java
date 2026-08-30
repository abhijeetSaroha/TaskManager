package com.taskmanager.api.dtos;

import com.taskmanager.api.models.enums.TaskStatus;
import java.time.LocalDate;

public record TaskRequest(
        String title,
        String description,
        TaskStatus status,
        LocalDate dueDate,
        Long assigneeId
) {}