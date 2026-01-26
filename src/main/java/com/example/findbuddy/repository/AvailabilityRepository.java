package com.example.findbuddy.repository;

import com.example.findbuddy.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    List<Availability> findByUserId(Long userId);

    @Query("""
            SELECT a
            FROM Availability a
            JOIN FETCH a.user u
            JOIN u.interests i
            WHERE LOWER(i.name) = LOWER(:interestName)
            AND u.id <> :currentUserId
            AND a.day = :day
            AND a.timeFrom <= CAST(:toTime AS time)
            AND a.timeTo >= CAST(:fromTime AS time)
            ORDER BY u.username, a.timeFrom
           """)
    List<Availability> findMatchingAvailability(
            @Param("interestName") String interestName,
            @Param("currentUserId") Long currentUserId,
            @Param("day") DayOfWeek day,
            @Param("fromTime") LocalTime fromTime,
            @Param("toTime") LocalTime toTime
    );


}