package com.taskmanager.api.dtos;

import java.util.List;

public record TeamResponse(
        Long id,
        String name,
        List<String> memberEmails
) {}