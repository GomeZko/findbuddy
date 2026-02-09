package com.example.findbuddy.controller;

import com.example.findbuddy.model.Availability;
import com.example.findbuddy.service.AvailabilityService;
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
    public String addAvailability(@RequestBody AvailabilityRequest request) {

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
            int day,
            String from,
            String to
    ) {}
}
