package com.gautam.bank.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Step 1 - JWT Service
    private final JwtService jwtService;

    // Step 2 - Load User from Database
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // Step 3 - Read Authorization Header
        String authHeader = request.getHeader("Authorization");

        // Step 4 - Check Bearer Token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Step 5 - Remove "Bearer "
        String jwt = authHeader.substring(7);

        // Step 6 - Extract Username
        String username = jwtService.extractUsername(jwt);

        if (username != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Step 7 - Load User
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            // Step 8 - Validate Token
            if (jwtService.isTokenValid(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                // Step 9 - Set Authentication
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
            }
        }

        // Step 10 - Continue Filter Chain
        filterChain.doFilter(request, response);
    }
}