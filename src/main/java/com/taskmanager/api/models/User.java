package com.taskmanager.api.models;

import com.taskmanager.api.models.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // A user can have multiple tasks assigned to them
    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL)
    private List<Task> tasks;
}