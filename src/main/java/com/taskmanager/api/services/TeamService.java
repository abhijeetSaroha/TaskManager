package com.taskmanager.api.services;

import com.taskmanager.api.dtos.TeamRequest;
import com.taskmanager.api.dtos.TeamResponse;

public interface TeamService {
    TeamResponse createTeam(TeamRequest request);
    TeamResponse addMemberToTeam(Long teamId, Long userId);
    TeamResponse getTeamById(Long teamId);
}