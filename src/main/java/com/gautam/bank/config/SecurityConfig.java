package com.gautam.bank.config;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gautam.bank.security.CustomUserDetailsService;
import com.gautam.bank.security.JwtAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

        // Step 1 - JWT Filter
        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        // Step 2 - UserDetailsService
        private final CustomUserDetailsService customUserDetailsService;

        // Step 3 - Password Encoder
        private final PasswordEncoder passwordEncoder;

        // Step 4 - Authentication Provider
        @Bean
        public AuthenticationProvider authenticationProvider() {

                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

                provider.setUserDetailsService(customUserDetailsService);
                provider.setPasswordEncoder(passwordEncoder);

                return provider;
        }

        // Step 5 - Authentication Manager
        @Bean
        public AuthenticationManager authenticationManager(
                        AuthenticationConfiguration configuration)
                        throws Exception {

                return configuration.getAuthenticationManager();
        }

        // Step 6 - Security Filter Chain
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http)
                        throws Exception {

                http
                                // Disable CSRF
                                .csrf(csrf -> csrf.disable())

                                // Stateless Authentication
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                                // Authorization Rules
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/auth/**").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/api/employees")
                                                .hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.POST, "/api/customers")
                                                .hasRole("EMPLOYEE")
                                                .requestMatchers(HttpMethod.POST, "/api/accounts")
                                                .hasRole("EMPLOYEE")
                                                .requestMatchers(
                                                                HttpMethod.POST,
                                                                "/api/transactions/deposit")
                                                .hasRole("EMPLOYEE")
                                                .requestMatchers(
                                                                HttpMethod.POST,
                                                                "/api/transactions/withdraw")
                                                .hasRole("EMPLOYEE")
                                                .requestMatchers(
                                                                HttpMethod.POST,
                                                                "/api/transactions/transfer")
                                                .hasRole("EMPLOYEE")
                                                .requestMatchers(
                                                                HttpMethod.POST,
                                                                "/api/customers/enable-internet-banking")
                                                .hasRole("EMPLOYEE")
                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/customer/profile")
                                                .hasRole("CUSTOMER")
                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/customer/accounts")
                                                .hasRole("CUSTOMER")
                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/customer/transactions")
                                                .hasRole("CUSTOMER")
                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/customer/beneficiaries")
                                                .hasRole("CUSTOMER")
                                                .requestMatchers(
                                                                HttpMethod.PUT,
                                                                "/api/customer/beneficiaries/**")
                                                .hasRole("CUSTOMER")
                                                .requestMatchers(
                                                                HttpMethod.DELETE,
                                                                "/api/customer/beneficiaries/**")
                                                .hasRole("CUSTOMER")
                                                .requestMatchers(
                                                                HttpMethod.POST,
                                                                "/api/customer/beneficiaries/transfer")
                                                .hasRole("CUSTOMER")
                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/customer/statements/mini/**")
                                                .hasRole("CUSTOMER")

                                                .requestMatchers(
                                                                HttpMethod.POST,
                                                                "/api/customer/statements/date-range")
                                                .hasRole("CUSTOMER")
                                                .requestMatchers(
                                                                HttpMethod.POST,
                                                                "/api/customer/statements/monthly")
                                                .hasRole("CUSTOMER")
                                                .requestMatchers(
                                                                HttpMethod.POST,
                                                                "/api/customer/statements/pdf")
                                                .hasRole("CUSTOMER")
                                                .requestMatchers(
                                                                HttpMethod.POST,
                                                                "/api/customer/statements/excel")
                                                .hasRole("CUSTOMER")
                                                .anyRequest().authenticated())

                                // Authentication Provider
                                .authenticationProvider(authenticationProvider())

                                // JWT Filter
                                .addFilterBefore(
                                                jwtAuthenticationFilter,
                                                UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}