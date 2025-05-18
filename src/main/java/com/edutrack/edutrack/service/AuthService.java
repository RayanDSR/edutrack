package com.edutrack.edutrack.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.edutrack.edutrack.config.JwtService;
import com.edutrack.edutrack.dto.AuthRequest;
import com.edutrack.edutrack.dto.AuthResponse;
import com.edutrack.edutrack.dto.RefreshTokenRequest;
import com.edutrack.edutrack.dto.RegisterRequest;
import com.edutrack.edutrack.exception.InvalidTokenException;
import com.edutrack.edutrack.exception.UserAlreadyExistsException;
import com.edutrack.edutrack.exception.UserNotFoundException;
import com.edutrack.edutrack.model.Token;
import com.edutrack.edutrack.model.TokenType;
import com.edutrack.edutrack.model.User;
import com.edutrack.edutrack.repository.TokenRepository;
import com.edutrack.edutrack.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final TokenRepository tokenRepository;
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
        String jwtRefreshToken = jwtService.generateRefreshToken(user);

        userRepository.save(user);

        saveUserToken(user, jwtToken, TokenType.BEARER);
        saveUserToken(user, jwtRefreshToken, TokenType.REFRESH);

        return AuthResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(jwtRefreshToken)
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
        String jwtRefreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken, TokenType.BEARER);
        saveUserToken(user, jwtRefreshToken, TokenType.REFRESH);

        return AuthResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(jwtRefreshToken)
            .build();
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        String userEmail = jwtService.extractUsername(refreshToken);

        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new InvalidTokenException("Invalid refresh token"));

        var isTokenValid = tokenRepository.findByToken(refreshToken)
            .map(t -> !t.isExpired() && !t.isRevoked() && t.getTokenType().equals(TokenType.REFRESH))
            .orElse(false);

        if (!jwtService.isTokenValid(refreshToken, user) || !isTokenValid) {
            throw new InvalidTokenException("Invalid refresh token");
        }

        String newAccessToken = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, newAccessToken, TokenType.BEARER);
        saveUserToken(user, newRefreshToken, TokenType.REFRESH);

        return AuthResponse.builder()
            .accessToken(newAccessToken)
            .refreshToken(newRefreshToken)
            .build();
    }

    private void saveUserToken(User user, String jwtToken, TokenType tokenType) {
        Token token = Token.builder()
            .user(user)
            .token(jwtToken)
            .tokenType(tokenType)
            .expired(false)
            .revoked(false)
            .build();

        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserToken = tokenRepository.findAllValidTokenByUser(user.getId());

        if (validUserToken.isEmpty()) {
            return;
        }

        validUserToken.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserToken);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found");
        }
        String email = authentication.getName();
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException(email));
    }
}
