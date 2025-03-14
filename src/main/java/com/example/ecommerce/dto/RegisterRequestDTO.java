package com.example.ecommerce.dto;

import com.example.ecommerce.enums.Role;

public record RegisterRequestDTO(
        String firstname,
        String lastname,
        String phoneNumber,
        String email,
        String password,
        Role role
) {
    public Role role() {
        return role != null ? role : Role.USER;
    }
}
