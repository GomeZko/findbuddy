package com.example.findbuddy.service;


import com.example.findbuddy.model.Availability;
import com.example.findbuddy.model.Interest;
import com.example.findbuddy.model.User;
import com.example.findbuddy.repository.InterestRepository;
import com.example.findbuddy.repository.UserRepository;
import com.example.findbuddy.service.DTO.AddAvailabilityRequest;
import com.example.findbuddy.service.DTO.MatchResult;
import com.example.findbuddy.service.DTO.UpdateUserRequest;
import com.example.findbuddy.service.DTO.UserResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleInfo;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final InterestRepository interestRepository;

    public UserService(UserRepository userRepository, InterestRepository interestRepository) {
        this.userRepository = userRepository;
        this.interestRepository = interestRepository;
    }

    @Transactional
    public User getUserWithInterests(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.getInterests().size();
        return user;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    public User updateUser(String username, UpdateUserRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if(request.getCity() != null) {
            user.setCity(request.getCity());
        }

        return userRepository.save(user);
    }

    public UserResponse getUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow();

        Set<String> interests = user.getInterests()
                .stream()
                .map(i -> i.getName())
                .collect(Collectors.toSet());
        List<String> availability = user.getAvailability()
                .stream()
                .map(a -> a.getDay() + " " + a.getTimeFrom() + " " + a.getTimeTo())
                .toList();

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getCity(),
                interests,
                availability
        );
    }

    @Transactional
    public User addInterest(String username, List<String> interestNames) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if(interestNames == null || interestNames.isEmpty()) {
            throw new RuntimeException("Interests list is empty");
        }

        for (String name : interestNames) {
            Interest interest = interestRepository
                    .findByNameIgnoreCase(name)
                    .orElseGet(() -> {
                        Interest newInterest = new Interest();
                        newInterest.setName(name);
                        return interestRepository.save(newInterest);
                    });
            user.getInterests().add(interest);
        }
        return userRepository.save(user);
    }

    public User addAvailability(String username, AddAvailabilityRequest request) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Availability availability = new Availability();
        availability.setDay(request.day());
        availability.setTimeFrom(request.timeFrom());
        availability.setTimeTo(request.timeTo());
        availability.setUser(user);

        user.getAvailability().add(availability);

        return userRepository.save(user);
    }

    public List<MatchResult> findMatches(String currentUsername, List<String> interestNames) {
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<User> allUsers = userRepository.findAll();

        return allUsers.stream()
                .filter(user -> !user.getUsername().equals(currentUsername))
                .map(user -> {

                    long matches = user.getInterests().stream()
                            .map(i -> i.getName().toLowerCase())
                            .filter(interestNames.stream().map(String::toLowerCase).toList()::contains)
                            .count();

                    int percent = (int)((matches * 100) / interestNames.size());

                    return new MatchResult(
                            user.getUsername(),
                            user.getCity(),
                            percent
                    );
                })
                .filter(result -> result.getMatchPercent() > 0)
                .sorted((a, b) -> b.getMatchPercent() - a.getMatchPercent())
                .toList();
    }

}
