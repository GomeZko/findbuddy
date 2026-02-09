package com.example.findbuddy.controller;

import com.example.findbuddy.service.InterestService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interests")
@CrossOrigin
public class InterestController {

    private final InterestService interestService;

    public InterestController(InterestService interestService) {
        this.interestService = interestService;
    }

    @PostMapping("/add")
    public String addInterest(@RequestBody InterestRequest request) {
        interestService.addInterestToUser(request.userId(), request.interest());
        return "Interest added";
    }

    public record InterestRequest(Long userId, String interest) {}
}
