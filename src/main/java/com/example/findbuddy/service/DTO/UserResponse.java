package com.example.findbuddy.service.DTO;

import java.util.List;

public class UserResponse {
    private Long id;
    private String username;
    private String city;
    private List<String> interests;

    public UserResponse(Long id, String username, String city,List<String> interests) {
        this.id = id;
        this.username = username;
        this.city = city;
        this.interests = interests;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getCity() {
        return city;
    }

    public List<String> getInterests() {
        return interests;
    }
}
