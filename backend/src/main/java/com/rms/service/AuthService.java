package com.rms.service;

import com.rms.dto.request.LoginRequest;
import com.rms.dto.request.RegisterRequest;
import com.rms.dto.response.AuthResponse;
import com.rms.dto.response.UserResponse;
import com.rms.exception.BadRequestException;
import com.rms.model.User;
import com.rms.model.Role;


import com.rms.repository.UserRepository;
import com.rms.security.JwtTokenProvider;
import com.rms.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }
        
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .displayName(request.getDisplayName())
                .role(Role.MEMBER)
                .isActive(true)
                .build();
        
        user = userRepository.save(user);
        
        String accessToken = tokenProvider.generateAccessToken(user.getId());
        String refreshToken = tokenProvider.generateRefreshToken(user.getId());
        
        return AuthResponse.of(accessToken, refreshToken, UserResponse.fromEntity(user));
    }
    
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new BadRequestException("User not found"));
        
        String accessToken = tokenProvider.generateAccessToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(userPrincipal.getId());
        
        return AuthResponse.of(accessToken, refreshToken, UserResponse.fromEntity(user));
    }
    
    public AuthResponse refreshToken(String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new BadRequestException("Invalid refresh token");
        }
        
        String userId = tokenProvider.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));
        
        String newAccessToken = tokenProvider.generateAccessToken(userId);
        String newRefreshToken = tokenProvider.generateRefreshToken(userId);
        
        return AuthResponse.of(newAccessToken, newRefreshToken, UserResponse.fromEntity(user));
    }
}
