package com.taskmanager.api.repositories;

import com.taskmanager.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query to find a user by their email for authentication
    Optional<User> findByEmail(String email);
}