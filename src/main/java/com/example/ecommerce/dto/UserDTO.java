package com.example.ecommerce.dto;

import com.example.ecommerce.entity.*;
import com.example.ecommerce.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Optional;
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
) {
    public static UserDTO fromEntity(User user) {
        return new UserDTO(
                user.getId(),
                user.getUuid(),
                PersonalDetailsDTO.fromEntity(user.getPersonal()),
                user.getEmail(),
                null,
                user.getRole(),
                user.getAddresses().stream().map(DeliveryAddress::getId).toList(),
                user.getOrders().stream().map(Order::getId).toList(),
                user.getDiscount().stream().map(Discount::getId).toList()
        );
    }

    public User toEntity() {
        return new User(
                Optional.ofNullable(personal)
                        .map(PersonalDetailsDTO::toEntity)
                        .orElseGet(PersonalDetails::empty),
                email,
                password,
                Optional.ofNullable(role).orElse(Role.USER)
        );
    }
}
