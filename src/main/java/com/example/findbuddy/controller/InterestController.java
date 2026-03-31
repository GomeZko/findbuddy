package com.example.findbuddy.controller;

import com.example.findbuddy.service.InterestService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interests")
@CrossOrigin
public class InterestController {

    private final InterestService interestService;

    public InterestController(InterestService interestService) {
        this.interestService = interestService;
    }

    @GetMapping
    public List<String> getAllInterests() {
        return interestService.getAllInterests();
    }

    @PostMapping("/add")
    public String addInterest(@RequestBody InterestRequest request, Authentication authentication) {
        interestService.addInterestToUser(authentication.getName(), request.interest());
        return "Interest added";
    }

    public record InterestRequest(String interest) {}
}
