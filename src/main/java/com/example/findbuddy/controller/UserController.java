package com.example.findbuddy.controller;



import com.example.findbuddy.model.User;
import com.example.findbuddy.service.DTO.UpdateUserRequest;
import com.example.findbuddy.service.DTO.UserResponse;
import com.example.findbuddy.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/profile")
    public UserResponse getProfile(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getCity(),
                user.getInterests()
                        .stream()
                        .map(interest -> interest.getName())
                        .collect(Collectors.toList())


        );
    }

    //Update user
    @PutMapping("/profile")
    public UserResponse updateProfile(@RequestBody UpdateUserRequest request,
                                      Authentication authentication) {
        String username = authentication.getName();
        User updatedUser = userService.updateUser(username, request);

        return new UserResponse(
                updatedUser.getId(),
                updatedUser.getUsername(),
                updatedUser.getCity(),
                updatedUser.getInterests()
                        .stream()
                        .map(interest -> interest.getName())
                        .toList()
        );
    }

}
