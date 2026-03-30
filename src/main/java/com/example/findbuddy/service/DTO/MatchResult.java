package com.example.findbuddy.service.DTO;

public class MatchResult {
    private String username;
    private String city;
    private int matchPercent;

    public MatchResult(String username, String city, int matchPercent) {
        this.username = username;
        this.city = city;
        this.matchPercent = matchPercent;
    }

    public String getUsername() {
        return username;
    }

    public String getCity() {
        return city;
    }

    public int getMatchPercent() {
        return matchPercent;
    }
}
