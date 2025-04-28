package com.edutrack.edutrack.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.edutrack.edutrack.config.JwtService;
import com.edutrack.edutrack.dto.AuthRequest;
import com.edutrack.edutrack.dto.AuthResponse;
import com.edutrack.edutrack.dto.RegisterRequest;
import com.edutrack.edutrack.exception.UserAlreadyExistsException;
import com.edutrack.edutrack.model.User;
import com.edutrack.edutrack.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(request.getEmail());
        }

        User user = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(request.getRole())
            .build();

        String jwtToken = jwtService.generateToken(user);

        userRepository.save(user);

        return AuthResponse.builder()
            .accessToken(jwtToken)
            .build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        String jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
            .accessToken(jwtToken)
            .build();
    }
}
