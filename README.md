# FindBuddy (Spring Boot + MS SQL Server + JWT)

FindBuddy is a REST-based backend application built with **Java Spring Boot** and **Microsoft SQL Server**.

The goal of the project is to help users find people to spend time with based on:

- Shared interests (e.g. Programming, Football, Music)
- Availability (day of week + time range)
- Match percentage (how many interests overlap)

This project demonstrates usage of:

- Relational database design (Many-to-Many and One-to-Many relationships)
- Spring Boot REST Controllers
- Spring Security with JWT authentication
- Hibernate / JPA ORM
- SQL queries (JOIN, GROUP BY, COUNT, ORDER BY)

---

## 🚀 Features

### 🔐 Authentication (JWT)

- Register a new user (username, password, city)
- Login with username + password
- Receive JWT token
- Access protected endpoints using Bearer Token

Example login response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```
Use token in requests:
    Authorization: Bearer YOUR_TOKEN

# 🎯 **Interests**

- Add one or multiple interests

- Show user profile with interests

- Find people by one interest

- Find people by multiple interests and calculate match %

# **📅 Availability**

- Add availability slot (day of week + time range)

- -View all availability slots

- -Find people by interest + availability overlap

# **🛠 Technologies**

- Java 23

- Spring Boot 3

- Spring Security

- JWT (jjwt 0.11.x)

- Spring Data JPA (Hibernate)

- Microsoft SQL Server

- Docker (for SQL Server)

- Maven

# **📦 Project Structure**
controller/      → REST Controllers

service/         → Business logic

repository/      → JPA repositories

model/           → Entities

config/          → Security configuration (JWT)

# **🔮 Future Improvements**

- Flutter mobile application

- Refresh tokens

- Role-based authorization

- Swagger documentation

- Deployment (Azure / Docker Compose)

