package com.example.ecommerce.dto;

public record ResetPasswordRequestDTO(
        String pin,
        String newPassword,
        String confirmationPassword
) {}
