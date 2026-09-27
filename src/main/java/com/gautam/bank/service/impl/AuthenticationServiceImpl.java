package com.gautam.bank.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.gautam.bank.dto.request.auth.LoginRequest;
import com.gautam.bank.dto.response.auth.LoginResponse;
import com.gautam.bank.entity.auth.User;
import com.gautam.bank.repository.UserRepository;
import com.gautam.bank.security.CustomUserDetailsService;
import com.gautam.bank.security.JwtService;
import com.gautam.bank.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    // Step 1 - Authentication Manager
    private final AuthenticationManager authenticationManager;

    // Step 2 - JWT Service
    private final JwtService jwtService;

    // Step 3 - UserDetailsService
    private final CustomUserDetailsService customUserDetailsService;

    // Step 4 - User Repository
    private final UserRepository userRepository;

    @Override
    public LoginResponse login(LoginRequest request) {

        // Step 5 - Authenticate username & password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        // Step 6 - Load UserDetails
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());

        // Step 7 - Generate JWT Token
        String token = jwtService.generateToken(userDetails);

        // Step 8 - Fetch User Entity
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Step 9 - Return Response
        return LoginResponse.builder()
                .username(user.getUsername())
                .role(user.getRole())
                .token(token)
                .tokenType("Bearer")
                .build();
    }
}