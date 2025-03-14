package com.example.ecommerce.controller;

import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.dto.Views;
import com.example.ecommerce.service.UserManagementService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
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
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserDTO userDTO) {
        UserDTO createdUser = userManagementService.create(userDTO);

        URI location = URI.create(String.format("/api/users/%s", createdUser.uuid()));
        return ResponseEntity.created(location).body(createdUser);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Void> update(@PathVariable UUID uuid, @RequestBody @Valid UserDTO userDTO) {
        userManagementService.put(uuid, userDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        userManagementService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
