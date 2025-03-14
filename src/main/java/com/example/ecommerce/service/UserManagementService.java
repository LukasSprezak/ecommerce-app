package com.example.ecommerce.service;

import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.TokenRepository;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class UserManagementService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;

    public UserManagementService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map(UserDTO::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    public UserDTO create(UserDTO userDTO) {
        if (userRepository.existsByUuid(userDTO.uuid())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with this UUID already exists");
        }

        User user = userDTO.toEntity();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return UserDTO.fromEntity(userRepository.save(user));
    }

    @Override
    @Transactional
    public void put(UUID uuid, UserDTO userDTO) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setPersonal(userDTO.personal().toEntity());
        UserDTO.fromEntity(userRepository.save(user));
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        tokenRepository.deleteAllByUser(user);
        userRepository.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getByUuid(UUID uuid) {
        return userRepository.findByUuid(uuid)
                .map(UserDTO::fromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
