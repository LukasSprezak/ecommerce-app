package com.example.ecommerce.dto;

import com.example.ecommerce.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public record UserDTO(
        @JsonView(Views.Public.class) Long id,
        @JsonView(Views.Public.class) UUID uuid,
        @JsonView(Views.Public.class) PersonalDetailsDTO personal,
        @JsonView(Views.Public.class) String email,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) @NotBlank(message = "Password cannot be blank") String password,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) Role role,
        @JsonView(Views.Extended.class) List<Long> addressIds,
        @JsonView(Views.Extended.class) List<Long> orderIds,
        @JsonView(Views.Extended.class) List<Long> discountIds
) {}
