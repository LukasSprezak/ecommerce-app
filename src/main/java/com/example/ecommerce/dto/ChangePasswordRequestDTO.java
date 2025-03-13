package com.example.ecommerce.dto;

public record ChangePasswordRequestDTO(
        String currentPassword,
        String newPassword,
        String confirmationPassword
) {}
