package com.taskmanager.api.services;

import com.taskmanager.api.dtos.TaskRequest;
import com.taskmanager.api.dtos.TaskResponse;
import com.taskmanager.api.exceptions.ResourceNotFoundException;
import com.taskmanager.api.factories.TaskFactory;
import com.taskmanager.api.models.Task;
import com.taskmanager.api.models.User;
import com.taskmanager.api.models.enums.TaskStatus;
import com.taskmanager.api.notifications.NotificationService;
import com.taskmanager.api.repositories.TaskRepository;
import com.taskmanager.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskReadService, TaskWriteService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskFactory taskFactory;
    private final NotificationService notificationService;

    @Override
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskFactory::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        return taskFactory.toResponse(task);
    }

    @Override
    public List<TaskResponse> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status).stream()
                .map(taskFactory::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponse createTask(TaskRequest request) {
        User assignee = null;
        if (request.assigneeId() != null) {
            assignee = userRepository.findById(request.assigneeId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.assigneeId()));
        }
        Task task = taskFactory.toEntity(request, assignee);
        return taskFactory.toResponse(taskRepository.save(task));
    }

    @Override
    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(request.status());
        task.setDueDate(request.dueDate());

        if (request.assigneeId() != null) {
            User assignee = userRepository.findById(request.assigneeId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.assigneeId()));
            task.setAssignee(assignee);
        }

        return taskFactory.toResponse(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    @Override
    public TaskResponse assignTaskToUser(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        task.setAssignee(user);
        Task savedTask = taskRepository.save(task);

        // Phase 5: Trigger Notification Strategy Pattern
        notificationService.notify("EMAIL", user.getEmail(),
                "You have been assigned a new task: " + savedTask.getTitle());

        return taskFactory.toResponse(savedTask);
    }
}