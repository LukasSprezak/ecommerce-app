package com.example.ecommerce.controller;

import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.dto.Views;
import com.example.ecommerce.service.UserManagementService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserManagementController {

    private final UserManagementService userManagementService;

    @Autowired
    public UserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @GetMapping
    @JsonView(Views.Public.class)
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> users = userManagementService.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{uuid}")
    @JsonView(Views.Extended.class)
    public ResponseEntity<UserDTO> getByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(userManagementService.getByUuid(uuid));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        UserDTO createdUser = userManagementService.create(userDTO);
        return ResponseEntity.created(URI.create("/api/users/" + createdUser.uuid())).body(createdUser);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Void> updateUser(@PathVariable UUID uuid, @RequestBody @Valid UserDTO userDTO) {
        userManagementService.put(uuid, userDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID uuid) {
        userManagementService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
