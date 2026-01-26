package com.example.findbuddy.repository;


import com.example.findbuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import java.time.DayOfWeek;
import java.time.LocalTime;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("""
        SELECT DISTINCT u
        FROM User u
        JOIN u.interests i
        WHERE i.name = :interestName
        AND u.id <> :currentUserId
        """)

    List<User> findUsersByInterest(@Param("interestName") String interestName,
                                   @Param("currentUserId") Long currentUserId);

    @Query("""
        SELECT u.username, u.city, COUNT(i.id)
        FROM User u
        JOIN u.interests i
        WHERE LOWER(i.name) IN :interestNames
        AND u.id <> :currentUserId
        GROUP BY u.username, u.city
        ORDER BY COUNT(i.id) DESC
        """)

    List<Object[]> findUsersByInterestsWithCount(
            @Param("interestNames") Collection<String> interestNames,
            @Param("currentUserId") Long currentUserId
    );

    @Query("""
        SELECT DISTINCT u
        FROM User u
        JOIN u.interests i
        JOIN Availability a ON a.user.id = u.id
        WHERE LOWER(i.name) = LOWER(:interestName)
        AND u.id <> :currentUserId
        AND a.day = :day
        AND a.timeFrom <= CAST(:toTime AS time)
        AND a.timeTo >= CAST(:fromTime AS time)
        """)

    List<User> findUsersByInterestAndAvailability(
            @Param("interestName") String interestName,
            @Param("currentUserId") Long currentUserId,
            @Param("day") DayOfWeek day,
            @Param("fromTime") LocalTime fromTime,
            @Param("toTime") LocalTime toTime
    );




}
