package com.example.findbuddy.service.DTO;

public class MatchResult {
    private final String username;
    private final String city;
    private final long matchesCount;
    private final int matchPercent;

    public MatchResult(String username, String city, long matchesCount, int matchPercent) {
        this.username = username;
        this.city = city;
        this.matchesCount = matchesCount;
        this.matchPercent = matchPercent;
    }

    public String getUsername() {
        return username;
    }

    public String getCity() {
        return city;
    }

    public long getMatchesCount() {
        return matchesCount;
    }

    public int getMatchPercent() {
        return matchPercent;
    }
}
