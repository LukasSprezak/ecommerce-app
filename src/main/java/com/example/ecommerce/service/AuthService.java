package com.example.ecommerce.service;

import com.example.ecommerce.dto.AuthRequestDTO;
import com.example.ecommerce.dto.AuthResponseDTO;
import com.example.ecommerce.dto.RegisterRequestDTO;
import com.example.ecommerce.entity.PersonalDetails;
import com.example.ecommerce.entity.Token;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.enums.TokenType;
import com.example.ecommerce.repository.TokenRepository;
import com.example.ecommerce.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserRepository userRepository,
            TokenRepository tokenRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponseDTO register(RegisterRequestDTO request) {

        PersonalDetails personalDetails = new PersonalDetails(
                request.firstname(),
                request.lastname(),
                request.phoneNumber()
        );

        User user = new User(
                personalDetails,
                request.email(),
                passwordEncoder.encode(request.password()),
                request.role()
        );

        User savedUser = userRepository.save(user);
        String accessToken = jwtService.generateToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(savedUser);

        saveUserToken(savedUser, accessToken, refreshToken);

        return new AuthResponseDTO(accessToken, refreshToken);
    }

    public AuthResponseDTO authenticate(AuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow();

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, accessToken, refreshToken);

        return new AuthResponseDTO(accessToken, refreshToken);
    }

    private void saveUserToken(User user, String accessToken, String refreshToken) {
        Token token = new Token(accessToken, refreshToken, TokenType.BEARER, false, false, user);
        tokenRepository.save(token);
    }


    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    public AuthResponseDTO refreshToken(HttpServletRequest request) {
        String refreshToken = extractBearerToken(request)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing or invalid Authorization header"));

        String userEmail = jwtService.extractUsername(refreshToken);
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        Token storedToken = tokenRepository.findByRefreshToken(refreshToken)
                .filter(token -> !token.isRevoked() && !token.isExpired())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Refresh token is invalid or expired"));

        String newAccessToken = jwtService.generateToken(user);
        storedToken.setAccessToken(newAccessToken);
        tokenRepository.save(storedToken);

        return new AuthResponseDTO(newAccessToken, null);
    }

    private Optional<String> extractBearerToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(header -> header.startsWith("Bearer "))
                .map(header -> header.substring(7));
    }
}
