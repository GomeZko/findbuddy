package com.example.findbuddy.controller;


import com.example.findbuddy.model.Availability;
import com.example.findbuddy.model.User;
import com.example.findbuddy.service.SearchService;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/search")
@CrossOrigin
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService){
        this.searchService = searchService;
    }

    //Searching by interest
    @GetMapping("/interest")
    public List<User> findByInterest(@RequestParam Long currentUserId, @RequestParam String interest)  {
        return searchService.findPeopleByInterest(currentUserId, interest);
    }

    //Interest + availability
    @GetMapping("/availability")
    public List<Availability> findByInterestAndAvailability(
            @RequestParam Long currentUserId,
            @RequestParam String interest,
            @RequestParam int day,
            @RequestParam String from,
            @RequestParam String to) {
        DayOfWeek dayOfWeek = DayOfWeek.of(day);
        LocalTime fromTime = LocalTime.parse(from);
        LocalTime toTime = LocalTime.parse(to);

        return  searchService.findAvailabilityMatches(
                currentUserId,
                interest,
                dayOfWeek,
                fromTime,
                toTime
        );
    }
}
