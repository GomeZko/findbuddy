package com.example.findbuddy.service.DTO;

import java.util.List;
import java.util.Set;

public record UserResponse (
        Long id,
        String username,
        String city,
        String bio,
        Set<String> interests,
        List<String> availability
) {}
