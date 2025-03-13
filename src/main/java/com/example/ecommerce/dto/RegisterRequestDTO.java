package com.example.ecommerce.dto;

import com.example.ecommerce.enums.Role;

public record RegisterRequestDTO(
        String firstname,
        String lastname,
        String email,
        String password,
        Role role
) {
    public Role role() {
        return role != null ? role : Role.USER;
    }
}
