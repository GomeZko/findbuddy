# FindBuddy (Spring Boot + MS SQL Server + JWT) 
Author: Aleksandr Gomžin

FindBuddy is a REST-based backend application built with **Java Spring Boot** and **Microsoft SQL Server**.

The goal of the project is to help users find people to spend time with based on:

- Shared interests (e.g. Programming, Football, Music)
- Availability (day of week + time range)
- Match percentage (how many interests overlap)

---

## Technologies

- Java 17
- Spring Boot 3.2
- Spring Security + JWT (jjwt 0.11.x)
- Spring Data JPA (Hibernate)
- Microsoft SQL Server
- Maven

---

## Getting Started

### 1. Set environment variables

Before running the app, set the following environment variables:

| Variable | Description |
|----------|-------------|
| `DB_USERNAME` | SQL Server username |
| `DB_PASSWORD` | SQL Server password |
| `JWT_SECRET` | Secret key for signing JWT (min 32 characters) |

**In IntelliJ IDEA:** Run → Edit Configurations → Environment variables

**In terminal:**
```bash
export DB_USERNAME=SA
export DB_PASSWORD=yourpassword
export JWT_SECRET=yourSecretKeyAtLeast32CharactersLong
```

### 2. Run the application

```bash
./mvnw spring-boot:run
```

### 3. Open Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

Use Swagger to explore and test all endpoints. Click **Authorize** and paste your JWT token to access protected endpoints.

---

## Authentication

### Register
```
POST /api/auth/register
```
```json
{
  "username": "john",
  "password": "secret123",
  "city": "Berlin"
}
```

### Login
```
POST /api/auth/login
```
Response:
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "550e8400-e29b-41d4-a716..."
}
```

- `accessToken` — valid for 1 hour, use in `Authorization: Bearer <token>` header
- `refreshToken` — valid for 7 days, use to get a new access token

### Refresh access token
```
POST /api/auth/refresh
```
```json
{
  "refreshToken": "550e8400-e29b-41d4-a716..."
}
```

---

## API Endpoints

All endpoints except `/api/auth/**` require `Authorization: Bearer <token>` header.

### Users
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users/profile` | Get current user profile |
| PUT | `/api/users/profile` | Update city |
| POST | `/api/users/interests` | Add interests |
| POST | `/api/users/availability` | Add availability slot |
| POST | `/api/users/search` | Find matches by interests |

### Interests
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/interests/add` | Add a single interest |

### Availability
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/availability/add` | Add availability slot |
| GET | `/api/availability/{userId}` | Get user availability |

### Search
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/search/interest` | Find people by single interest |
| GET | `/api/search/match` | Find people by multiple interests with match % |

---

## Project Structure

```
controller/   → REST Controllers + GlobalExceptionHandler
service/      → Business logic
service/DTO/  → Request and response objects
repository/   → JPA repositories
model/        → Entities (User, Interest, Availability)
config/       → Security and Swagger configuration
exception/    → Custom exceptions (UserNotFoundException, etc.)
```

---

## Future Improvements

- Flutter mobile application
- Role-based authorization
- Deployment (Azure / Docker Compose)
