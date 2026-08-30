package com.taskmanager.api.repositories;

import com.taskmanager.api.models.Task;
import com.taskmanager.api.models.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Spring translates this method name into a SQL query automatically
    List<Task> findByStatus(TaskStatus status);
}