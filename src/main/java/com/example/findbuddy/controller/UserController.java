package com.example.findbuddy.controller;
import com.example.findbuddy.model.User;
import com.example.findbuddy.service.DTO.*;
import com.example.findbuddy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    // =============================
    // GET PROFILE (from JWT)
    // =============================
    @GetMapping("/profile")
    public UserResponse getProfile(Authentication authentication) {

        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Unauthorized");
        }

        String username = authentication.getName();
        User user = userService.findByUsername(username);

        return mapToResponse(user);
    }

    // =============================
    // UPDATE PROFILE
    // =============================
    @PutMapping("/profile")
    public UserResponse updateProfile(@Valid @RequestBody UpdateUserRequest request,
                                      Authentication authentication) {

        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Unauthorized");
        }

        String username = authentication.getName();
        User updatedUser = userService.updateUser(username, request);

        return mapToResponse(updatedUser);
    }

    // =============================
    // PRIVATE MAPPER METHOD
    // =============================
    private UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getCity(),
                user.getInterests()
                        .stream()
                        .map(i -> i.getName())
                        .collect(Collectors.toSet()),
                user.getAvailability()
                        .stream()
                        .map(a -> a.getDay() + " " + a.getTimeFrom() + " " + a.getTimeTo())
                        .toList()
        );
    }

    @PostMapping("/interests")
    public UserResponse addInterest(@Valid @RequestBody AddInterestRequest request,
                                    Authentication authentication) {
        String username =  authentication.getName();

        User updatedUser = userService.addInterest(username, request.interests());

        return mapToResponse(updatedUser);
    }

    @PostMapping("/availability")
    public UserResponse addAvailability(@Valid @RequestBody AddAvailabilityRequest request,
                                    Authentication authentication) {
        String username =  authentication.getName();
        User updatedUser = userService.addAvailability(username, request);

        return mapToResponse(updatedUser);
    }

    @PostMapping("/search")
    public List<MatchResult> search(@Valid @RequestBody SearchRequest request, Authentication authentication) {
        String username = authentication.getName();

        return userService.findMatches(username, request.getInterests());
    }
}
