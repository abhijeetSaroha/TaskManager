package com.taskmanager.api.controllers;

import com.taskmanager.api.dtos.AuthRequest;
import com.taskmanager.api.dtos.AuthResponse;
import com.taskmanager.api.dtos.RegisterRequest;
import com.taskmanager.api.models.User;
import com.taskmanager.api.models.enums.Role;
import com.taskmanager.api.repositories.UserRepository;
import com.taskmanager.api.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        var user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.valueOf(request.role()))
                .build();
        userRepository.save(user);
        var token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        var token = jwtUtil.generateToken(request.email());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}