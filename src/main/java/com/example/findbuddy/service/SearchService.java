package com.example.findbuddy.service;

import com.example.findbuddy.model.User;
import com.example.findbuddy.repository.UserRepository;
import com.example.findbuddy.service.DTO.MatchResult;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import java.time.DayOfWeek;
import java.time.LocalTime;


@Service
public class SearchService {

    private final UserRepository userRepository;

    public SearchService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> findPeopleByInterest(Long currentUserId, String interestName) {
        return userRepository.findUsersByInterest(interestName, currentUserId);
    }

    public List<MatchResult> findPeopleByMultipleInterests(Long currentUserId, String input) {

        List<String> interests = Arrays.stream(input.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .map(String::toLowerCase)
                .distinct()
                .toList();

        if (interests.isEmpty()) {
            return List.of();
        }

        var raw = userRepository.findUsersByInterestsWithCount(interests, currentUserId);

        int total = interests.size();

        return raw.stream()
                .map(r -> {
                    String username = (String) r[0];
                    String city = (String) r[1];
                    long matches = ((Number) r[2]).longValue();

                    int percent = (int) Math.round((matches * 100.0) / total);

                    return new MatchResult(username, city, matches, percent);
                })
                .toList();
    }

    public List<User> findPeopleByInterestAndAvailability(Long currentUserId, String interestName, DayOfWeek day, LocalTime from, LocalTime to) {
        return userRepository.findUsersByInterestAndAvailability(interestName, currentUserId, day, from, to);
    }

}
