package com.taskmanager.api.services;

import com.taskmanager.api.dtos.TaskResponse;
import com.taskmanager.api.exceptions.ResourceNotFoundException;
import com.taskmanager.api.factories.TaskFactory;
import com.taskmanager.api.models.Task;
import com.taskmanager.api.models.enums.TaskStatus;
import com.taskmanager.api.notifications.NotificationService;
import com.taskmanager.api.repositories.TaskRepository;
import com.taskmanager.api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskFactory taskFactory;
    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task mockTask;
    private TaskResponse mockTaskResponse;

    @BeforeEach
    void setUp() {
        mockTask = Task.builder()
                .id(1L)
                .title("Test Task")
                .description("Test Description")
                .status(TaskStatus.OPEN)
                .dueDate(LocalDate.now())
                .build();

        mockTaskResponse = new TaskResponse(
                1L, "Test Task", "Test Description", TaskStatus.OPEN, LocalDate.now(), null
        );
    }

    @Test
    void getTaskById_WhenTaskExists_ReturnsTaskResponse() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(mockTask));
        when(taskFactory.toResponse(mockTask)).thenReturn(mockTaskResponse);

        // Act
        TaskResponse result = taskService.getTaskById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Test Task", result.title());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void getTaskById_WhenTaskDoesNotExist_ThrowsException() {
        // Arrange
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> taskService.getTaskById(99L));
        verify(taskRepository, times(1)).findById(99L);
    }
}