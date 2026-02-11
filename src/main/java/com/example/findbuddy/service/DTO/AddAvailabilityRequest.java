package com.example.findbuddy.service.DTO;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record AddAvailabilityRequest (
        DayOfWeek day,
        LocalTime timeFrom,
        LocalTime timeTo) {}
