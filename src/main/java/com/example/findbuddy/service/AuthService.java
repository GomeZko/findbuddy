package com.example.findbuddy.service;


import com.example.findbuddy.exception.UsernameAlreadyExistsException;
import com.example.findbuddy.model.User;
import com.example.findbuddy.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder encoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public User register(String username, String password, String city) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException(username);
        }
        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(encoder.encode(password));
        user.setCity(city);
        return userRepository.save(user);
    }

    public Optional<User> login(String username, String password) {
        return userRepository.findByUsername(username)
            .filter(u -> encoder.matches(password, u.getPasswordHash()))
            .map(u -> {
                u.setRefreshToken(jwtService.generateRefreshToken());
                return userRepository.save(u);
            });
    }

    public Optional<String> refresh(String refreshToken) {
        return userRepository.findByRefreshToken(refreshToken)
            .map(u -> jwtService.generateToken(u.getUsername()));
    }

}
