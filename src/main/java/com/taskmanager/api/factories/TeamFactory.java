package com.taskmanager.api.factories;

import com.taskmanager.api.dtos.TeamResponse;
import com.taskmanager.api.models.Team;
import com.taskmanager.api.models.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class TeamFactory {

    public TeamResponse toResponse(Team team) {
        var emails = team.getMembers() != null ?
                team.getMembers().stream().map(User::getEmail).collect(Collectors.toList())
                : Collections.<String>emptyList();

        return new TeamResponse(team.getId(), team.getName(), emails);
    }
}