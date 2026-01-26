# FindBuddy (Spring Boot + MS SQL Server)

FindBuddy is a simple console-based application built with **Java Spring Boot** and **Microsoft SQL Server**.
The goal of the project is to help users find people to spend time with based on:

- Shared interests (e.g. Programming, Football, Music)
- Availability (day of week + time range)
- Match percentage (how many interests overlap)

This project demonstrates usage of:
- Relational database design (Many-to-Many and One-to-Many relationships)
- Spring Boot services and repositories
- Hibernate / JPA ORM
- SQL queries (JOIN, GROUP BY, COUNT, ORDER BY)

---

## Features

### Authentication
- Register a new user (username, password, city)
- Login with username + password
- Logout

### Interests
- Add one or multiple interests (comma-separated)
- Show profile with interests
- Find people by one interest
- Find people by multiple interests and calculate match %

### Availability
- Add availability slot (day of week + time range)
- View all availability slots
- Find people by interest + availability overlap

---

## Technologies
- Java 23
- Spring Boot
- Spring Data JPA (Hibernate)
- Microsoft SQL Server
- Docker (for SQL Server)
- Azure Data Studio (optional, for DB management)

---

## Database Model (Entities)

### User
- id
- username
- passwordHash
- city

### Interest
- id
- name

### Availability
- id
- day (DayOfWeek)
- timeFrom (TIME)
- timeTo (TIME)
- user_id (FK)

### Relationships
- User ↔ Interest (Many-to-Many) via `user_interests`
- User → Availability (One-to-Many)

---

## Console Menu

After launching the application, you will see:

===FindBuddy v1.0===

1. Register
2. Login
3. Exit


After login:

=== User Menu ===

1. Show profile
2. Add interest
3. Find people by interest
4. Find people by multiple interests (match %)
5. Add availability
6. Show my availability
7. Find people by interest + availability
8. Logout
---
## How Matching Works

### 1) Find people by single interest
Example input:
- Interest: `Programming`

The app searches all users who have this interest (excluding the current user).

### 2) Find people by multiple interests (match %)
Example input:
- Interests: `programming, football, baseball`

The app counts how many interests match for each user and calculates:

matchPercent = (matchesCount / inputInterestsCount) * 100

