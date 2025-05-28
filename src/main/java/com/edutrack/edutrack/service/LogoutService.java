package com.edutrack.edutrack.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.edutrack.edutrack.config.JwtService;
import com.edutrack.edutrack.model.TokenType;
import com.edutrack.edutrack.model.User;
import com.edutrack.edutrack.repository.TokenRepository;
import com.edutrack.edutrack.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public void logout(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }

        String jwt = authHeader.substring(7);

        var isTokenValid = tokenRepository.findByToken(jwt)
                .map(t -> !t.isExpired() && !t.isRevoked() && t.getTokenType().equals(TokenType.BEARER))
                .orElse(false);

        if (!isTokenValid) {
            return;
        }

        String userName = jwtService.extractUsername(authHeader.substring(7));
        User user = userRepository.findByEmail(userName).orElse(null);

        var validUserToken = tokenRepository.findAllValidTokenByUser(user);

        if (validUserToken.isEmpty()) {
            return;
        }

        validUserToken.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserToken);
        SecurityContextHolder.clearContext();
    }
}
