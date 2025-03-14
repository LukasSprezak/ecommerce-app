package com.example.ecommerce.controller;

import com.example.ecommerce.dto.ChangePasswordRequestDTO;
import com.example.ecommerce.dto.ResetPasswordRequestDTO;
import com.example.ecommerce.service.UserSecurityService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserSecurityService userService;

    public UserController(UserSecurityService userService) {
        this.userService = userService;
    }

    @PostMapping("/reset-password-request")
    @Transactional
    public ResponseEntity<Void> requestPasswordReset(@RequestParam String email) {
        userService.requestPasswordReset(email);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    @Transactional
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequestDTO request) {
        userService.resetPassword(request);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/change-password")
    @Transactional
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequestDTO request, Principal principal) {
        userService.changePassword(request, principal);

        return ResponseEntity.ok().build();
    }
}
