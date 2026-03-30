package com.example.findbuddy.service.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record AddInterestRequest (
        @NotEmpty(message = "Interests list must not be empty")
        List<@NotBlank(message = "Interest name must not be blank") String> interests
) {}
