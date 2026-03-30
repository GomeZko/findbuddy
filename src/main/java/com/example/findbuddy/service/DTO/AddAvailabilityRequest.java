package com.example.findbuddy.service.DTO;

import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record AddAvailabilityRequest (
        @NotNull(message = "Day is required")
        DayOfWeek day,
        @NotNull(message = "Start time is required")
        LocalTime timeFrom,
        @NotNull(message = "End time is required")
        LocalTime timeTo) {}
