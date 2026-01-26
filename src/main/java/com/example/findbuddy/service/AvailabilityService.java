package com.example.findbuddy.service;

import com.example.findbuddy.model.Availability;
import com.example.findbuddy.repository.AvailabilityRepository;
import com.example.findbuddy.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Service
public class AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final UserRepository userRepository;

    public AvailabilityService(AvailabilityRepository availabilityRepository, UserRepository userRepository) {
        this.availabilityRepository = availabilityRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void addAvailability(Long userId, DayOfWeek day, LocalTime from, LocalTime to) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Availability slot = new Availability(user, day, from, to);
        availabilityRepository.save(slot);
    }

    public List<Availability> getAvailability(Long userId) {
        return availabilityRepository.findByUserId(userId);
    }
}
