package com.taskmanager.api.services;

import com.taskmanager.api.dtos.TeamRequest;
import com.taskmanager.api.dtos.TeamResponse;
import com.taskmanager.api.exceptions.ResourceNotFoundException;
import com.taskmanager.api.factories.TeamFactory;
import com.taskmanager.api.models.Team;
import com.taskmanager.api.models.User;
import com.taskmanager.api.repositories.TeamRepository;
import com.taskmanager.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamFactory teamFactory;

    @Override
    public TeamResponse createTeam(TeamRequest request) {
        Team team = Team.builder()
                .name(request.name())
                .build();
        return teamFactory.toResponse(teamRepository.save(team));
    }

    @Override
    public TeamResponse addMemberToTeam(Long teamId, Long userId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + teamId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        team.getMembers().add(user);
        return teamFactory.toResponse(teamRepository.save(team));
    }

    @Override
    public TeamResponse getTeamById(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + teamId));
        return teamFactory.toResponse(team);
    }
}