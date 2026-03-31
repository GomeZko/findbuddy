package com.example.findbuddy.controller;

import com.example.findbuddy.model.User;
import com.example.findbuddy.service.SearchService;
import com.example.findbuddy.service.UserService;
import com.example.findbuddy.service.DTO.MatchResult;
import com.example.findbuddy.service.DTO.UserResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;
    private final UserService userService;

    public SearchController(SearchService searchService, UserService userService) {
        this.searchService = searchService;
        this.userService = userService;
    }

    @GetMapping("/interest")
    public List<UserResponse> searchByInterest(
            @RequestParam String interest,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Authentication auth
    ) {
        User user = userService.findByUsername(auth.getName());
        return searchService.findPeopleByInterest(user.getId(), interest, page, size)
                .stream().map(this::mapToResponse).toList();
    }

    @GetMapping("/availability")
    public List<UserResponse> searchByInterestAndAvailability(@RequestParam String interest,
                                                              @RequestParam int day,
                                                              @RequestParam String from,
                                                              @RequestParam String to,
                                                              Authentication auth) {
        User user = userService.findByUsername(auth.getName());
        return searchService.findPeopleByInterestAndAvailability(
                        user.getId(), interest, DayOfWeek.of(day), LocalTime.parse(from), LocalTime.parse(to))
                .stream().map(this::mapToResponse).toList();
    }

    @GetMapping("/match")
    public List<MatchResult> searchByMultipleInterests(
            @RequestParam String interests,
            Authentication auth
    ) {
        User user = userService.findByUsername(auth.getName());
        return searchService.findPeopleByMultipleInterests(user.getId(), interests);
    }

    private UserResponse mapToResponse(User u) {
        return new UserResponse(
                u.getId(),
                u.getUsername(),
                u.getCity(),
                u.getBio(),
                u.getInterests().stream().map(i -> i.getName()).collect(Collectors.toSet()),
                u.getAvailability().stream().map(a -> a.getDay() + " " + a.getTimeFrom() + " " + a.getTimeTo()).toList()
        );
    }
}