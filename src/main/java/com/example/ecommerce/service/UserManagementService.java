package com.example.ecommerce.service;

import com.example.ecommerce.dto.PersonalDetailsDTO;
import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.entity.DeliveryAddress;
import com.example.ecommerce.entity.Discount;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.TokenRepository;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
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
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    @Transactional
    public UserDTO create(UserDTO userDTO) {
        if (userRepository.findByUuid(userDTO.uuid()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with this UUID already exists");
        }

        User user = User.of(UUID.randomUUID(), userDTO.personal().toEntity(), userDTO.email(), passwordEncoder.encode(userDTO.password()));
        User savedUser = userRepository.save(user);

        return convertToDTO(savedUser);
    }

    @Override
    @Transactional
    public UserDTO put(UUID uuid, UserDTO userDTO) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setPersonal(userDTO.personal().toEntity());
        userRepository.save(user);

        return convertToDTO(user);
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
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUuid(),
                PersonalDetailsDTO.fromEntity(user.getPersonal()),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                Optional.ofNullable(user.getAddresses())
                        .orElse(List.of())
                        .stream()
                        .map(DeliveryAddress::getId)
                        .toList(),
                Optional.ofNullable(user.getOrders())
                        .orElse(List.of())
                        .stream()
                        .map(Order::getId)
                        .toList(),
                Optional.ofNullable(user.getDiscount())
                        .orElse(List.of())
                        .stream()
                        .map(Discount::getId)
                        .toList()
        );
    }
}
