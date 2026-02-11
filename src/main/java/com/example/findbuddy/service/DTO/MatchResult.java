package com.example.findbuddy.service.DTO;

public record MatchResult(
        Long userId,
        String username,
        String city,
        int matchPercent
) {}
