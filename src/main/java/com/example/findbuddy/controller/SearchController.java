package com.example.findbuddy.controller;

import com.example.findbuddy.model.User;
import com.example.findbuddy.service.SearchService;
import com.example.findbuddy.service.UserService;
import com.example.findbuddy.service.DTO.MatchResult;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<User> searchByInterest(
            @RequestParam String interest,
            Authentication auth
    ) {

        String username = auth.getName();

        User user = userService.findByUsername(username);

        return searchService.findPeopleByInterest(user.getId(), interest);
    }

    @GetMapping("/match")
    public List<MatchResult> searchByMultipleInterests(
            @RequestParam String interests,
            Authentication auth
    ) {

        String username = auth.getName();

        User user = userService.findByUsername(username);

        return searchService.findPeopleByMultipleInterests(user.getId(), interests);
    }
}