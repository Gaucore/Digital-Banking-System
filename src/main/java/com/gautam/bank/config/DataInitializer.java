package com.gautam.bank.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.gautam.bank.entity.auth.User;
import com.gautam.bank.enums.UserRole;
import com.gautam.bank.enums.UserStatus;
import com.gautam.bank.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    // Step 1 - Logger
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    // Step 2 - User Repository
    private final UserRepository userRepository;

    // Step 3 - Password Encoder
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // Step 4 - Check if Default Admin Already Exists
        if (userRepository.findByUsername("admin").isPresent()) {

            log.info("Default Admin already exists.");
            return;
        }

        // Step 5 - Create Default Admin
        User admin = new User();

        admin.setUsername("admin");

        admin.setPassword(passwordEncoder.encode("Admin@123"));

        admin.setRole(UserRole.ADMIN);

        admin.setStatus(UserStatus.ACTIVE);

        // Step 6 - Save Default Admin
        userRepository.save(admin);

        // Step 7 - Log Success Message
        log.info("======================================");
        log.info("Default Admin Created Successfully");
        log.info("Username : admin");
        log.info("Password : Admin@123");
        log.info("Role     : ADMIN");
        log.info("======================================");
    }
}