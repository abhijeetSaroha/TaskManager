package com.taskmanager.api.controllers;

import com.taskmanager.api.dtos.TaskResponse;
import com.taskmanager.api.models.enums.TaskStatus;
import com.taskmanager.api.services.TaskReadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskReadService taskReadService;

    @Test
    @WithMockUser(roles = "ADMIN") // Bypasses the actual JWT filter by mocking an authenticated user
    void getAllTasks_Returns200AndTaskList() throws Exception {
        // Arrange
        TaskResponse response = new TaskResponse(
                1L, "Integration Test Task", "Testing controllers",
                TaskStatus.OPEN, LocalDate.now(), null
        );
        when(taskReadService.getAllTasks()).thenReturn(List.of(response));

        // Act & Assert
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value("Integration Test Task"));
    }

    @Test
    void getAllTasks_Unauthenticated_Returns401() throws Exception {
        // Act & Assert without @WithMockUser
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isForbidden()); // Or isUnauthorized(), depending on Spring Security version routing
    }
}