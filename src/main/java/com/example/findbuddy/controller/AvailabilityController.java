package com.example.findbuddy.controller;

import com.example.findbuddy.model.Availability;
import com.example.findbuddy.service.AvailabilityService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/availability")
@CrossOrigin
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @PostMapping("/add")
    public String addAvailability(@Valid @RequestBody AvailabilityRequest request) {

        availabilityService.addAvailability(
                request.userId(),
                DayOfWeek.of(request.day()),
                LocalTime.parse(request.from()),
                LocalTime.parse(request.to())
        );

        return "Availability added";
    }

    @GetMapping("/{userId}")
    public List<Availability> getAvailability(@PathVariable Long userId) {
        return availabilityService.getAvailability(userId);
    }

    public record AvailabilityRequest(
            Long userId,

            @Min(value = 1, message = "Day must be between 1 and 7")
            @Max(value = 7, message = "Day must be between 1 and 7")
            int day,

            @NotBlank(message = "Start time is required")
            @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "Time format must be HH:mm")
            String from,

            @NotBlank(message = "End time is required")
            @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "Time format must be HH:mm")
            String to
    ) {}
}
