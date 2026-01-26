package com.example.findbuddy.model;

import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "availability")
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek day;

    @Column(name = "time_from", columnDefinition = "time")
    private LocalTime timeFrom;

    @Column(name = "time_to", columnDefinition = "time")
    private LocalTime timeTo;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Availability() {
    }

    public Availability(User user, DayOfWeek day, LocalTime timeFrom, LocalTime timeTo) {
        this.user = user;
        this.day = day;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
    }

    public Long getId() {
        return id;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public LocalTime getTimeFrom() {
        return timeFrom;
    }

    public LocalTime getTimeTo() {
        return timeTo;
    }

    public User getUser() {
        return user;
    }
}
