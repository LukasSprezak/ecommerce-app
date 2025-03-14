package com.example.ecommerce.component;

import com.example.ecommerce.entity.PersonalDetails;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.enums.Role;
import com.example.ecommerce.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class CreateAdmin {

    private static final Logger logger = LoggerFactory.getLogger(CreateAdmin.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String ADMIN_EMAIL = "admin@example.com";
    private static final String DEFAULT_PASSWORD = "admin";

    public CreateAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    @Transactional
    public void createAdminUser() {
        Optional<User> existingAdmin = userRepository.findByEmail(ADMIN_EMAIL);

        if (existingAdmin.isPresent()) {
            logger.info("ADMIN user already exists: {}", ADMIN_EMAIL);
            return;
        }

        try {
            PersonalDetails personalDetails = new PersonalDetails(
                    "Admin",
                    "Smith",
                    "123456789"
            );

            User adminUser = new User(
                    personalDetails,
                    ADMIN_EMAIL,
                    passwordEncoder.encode(DEFAULT_PASSWORD),
                    Role.ADMIN
            );

            userRepository.save(adminUser);
            logger.info("ADMIN user created successfully. Email: {}", ADMIN_EMAIL);
        } catch (Exception exception) {
            logger.error("Failed to create ADMIN user", exception);
        }
    }
}
