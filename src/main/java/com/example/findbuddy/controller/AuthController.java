package com.example.findbuddy.controller;

import com.example.findbuddy.model.User;
import com.example.findbuddy.service.AuthService;
import com.example.findbuddy.service.JwtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request.username(), request.password(), request.city());
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        Optional<User> user = authService.login(request.username(), request.password());

        if (user.isPresent()) {
            String accessToken = jwtService.generateToken(user.get().getUsername());
            String refreshToken = user.get().getRefreshToken();
            return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken));
        }

        return ResponseEntity.status(401).body("Invalid credentials");
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest request) {
        return authService.refresh(request.refreshToken())
            .map(newToken -> ResponseEntity.ok(new JwtResponse(newToken, request.refreshToken())))
            .orElse(ResponseEntity.status(401).build());
    }

    public record RegisterRequest(@NotBlank(message = "Username is required") @Size(min = 6, max = 30, message = "Username must be 3-30 characters long") String username,
                                  @NotBlank(message = "Password is required") @Size(min = 6, message = "Password must be at least 6 characters long") String password,
                                  @NotBlank(message = "City is required") String city) {}
    public record LoginRequest(@NotBlank(message = "Username is required") String username,
                               @NotBlank(message = "Password is required") String password) {}
    public record JwtResponse(String accessToken, String refreshToken) {}
    public record RefreshRequest(String refreshToken) {}
}
