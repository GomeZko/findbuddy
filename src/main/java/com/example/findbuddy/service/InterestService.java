package com.example.findbuddy.service;

import com.example.findbuddy.model.Interest;
import com.example.findbuddy.model.User;
import com.example.findbuddy.repository.InterestRepository;
import com.example.findbuddy.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class InterestService {

    private final UserRepository userRepository;
    private final InterestRepository interestRepository;

    public InterestService(UserRepository userRepository,
                           InterestRepository interestRepository) {
        this.userRepository = userRepository;
        this.interestRepository = interestRepository;
    }

    @Transactional
    public void addInterestToUser(Long userId, String interestName) {


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        Interest interest = interestRepository.findByNameIgnoreCase(interestName)
                .orElseGet(() -> {
                    Interest i = new Interest();
                    i.setName(interestName);
                    return interestRepository.save(i);
                });


        user.getInterests().add(interest);

    }
}
