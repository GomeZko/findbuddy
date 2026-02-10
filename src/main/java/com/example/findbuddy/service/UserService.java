package com.example.findbuddy.service;


import com.example.findbuddy.model.User;
import com.example.findbuddy.repository.UserRepository;
import com.example.findbuddy.service.DTO.UpdateUserRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
