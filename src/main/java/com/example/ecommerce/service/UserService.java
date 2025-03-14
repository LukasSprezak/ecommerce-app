package com.example.ecommerce.service;

import com.example.ecommerce.dto.UserDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserDTO> getAll();
    UserDTO create(UserDTO userDTO);
    UserDTO put(UUID uuid, UserDTO userDTO);
    void delete(UUID uuid);
    UserDTO getByUuid(UUID uuid);
}
