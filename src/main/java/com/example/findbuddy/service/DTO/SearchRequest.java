package com.example.findbuddy.service.DTO;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class SearchRequest {
    @NotEmpty(message = "Interests list must not be empty")
    private List<String> interests;

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }
}

