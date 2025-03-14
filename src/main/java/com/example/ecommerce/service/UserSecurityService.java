package com.example.ecommerce.service;

import com.example.ecommerce.dto.ChangePasswordRequestDTO;
import com.example.ecommerce.dto.ResetPasswordRequestDTO;
import com.example.ecommerce.entity.PasswordResetToken;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.PasswordResetTokenRepository;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.security.SecureRandom;

@Service
public class UserSecurityService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public UserSecurityService(
            PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            EmailService emailService,
            PasswordResetTokenRepository passwordResetTokenRepository
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    public void requestPasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        String pin = generatePin();

        PasswordResetToken resetToken = new PasswordResetToken(user, pin);
        passwordResetTokenRepository.save(resetToken);

        emailService.sendEmail(user.getEmail(), "Password Reset Code", "Your PIN: " + pin);
    }

    public void resetPassword(ResetPasswordRequestDTO request) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(request.pin())
                .orElseThrow(() -> new IllegalStateException("Invalid or expired PIN"));

        User user = resetToken.getUser();

        if (!request.newPassword().equals(request.confirmationPassword())) {
            throw new IllegalStateException("Passwords do not match");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);
    }

    public void changePassword(ChangePasswordRequestDTO request, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }

        if (!request.newPassword().equals(request.confirmationPassword())) {
            throw new IllegalStateException("Passwords do not match");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }

    private String generatePin() {
        SecureRandom random = new SecureRandom();
        int pin = 100_000 + random.nextInt(900_000);

        return String.valueOf(pin);
    }
}
