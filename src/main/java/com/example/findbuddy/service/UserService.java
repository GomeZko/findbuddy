package com.example.findbuddy.service;


import com.example.findbuddy.model.User;
import com.example.findbuddy.repository.UserRepository;
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
        user.getInterests().size(); // принудительно загрузить interests
        return user;
    }
}
