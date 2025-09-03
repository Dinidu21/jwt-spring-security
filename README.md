# Order Management API

## Overview
This is a Spring Boot application that provides a RESTful API for managing orders with JWT-based authentication. The application allows users to sign up, sign in, and perform CRUD operations on orders. It supports role-based access control with two roles: `USER` and `ADMIN`. Users can manage their own orders, while admins have additional privileges to view and manage all orders.

## Features
- **User Authentication**: Sign up and sign in with JWT token generation.
- **Role-Based Access Control**: Supports `ROLE_USER` and `ROLE_ADMIN`.
- **Order Management**:
  - Create, read, update, and delete orders.
  - Users can view and manage their own orders.
  - Admins can view and manage all orders.
- **Security**: Uses Spring Security with JWT for secure API access.

## Technologies Used
- **Java**: 17 or higher
- **Spring Boot**: 3.x
- **Spring Security**: For authentication and authorization
- **JWT (JSON Web Tokens)**: For secure user authentication
- **Spring Data JPA**: For database operations
- **Maven**: Dependency management

### API Endpoints

#### Authentication
- **POST /api/auth/signup**  
  Register a new user.  
  **Body**:
  ```json
  {
    "username": "testuser",
    "email": "testuser@example.com",
    "password": "password123",
    "role": ["user"]
  }
  ```
  **Response**: Success message or error if username/email is taken.

- **POST /api/auth/signin**  
  Authenticate a user and return JWT and refresh tokens.  
  **Body**:
  ```json
  {
    "username": "testuser",
    "password": "password123"
  }
  ```
  **Response**:
  ```json
  {
    "token": "jwt-token-here",
    "refreshToken": "refresh-token-here"
  }
  ```

- **POST /api/auth/refresh-token**  
  Obtain a new access token using a refresh token.  
  **Body**:
  ```json
  {
    "refreshToken": "refresh-token-here"
  }
  ```
  **Response**:
  ```json
  {
    "token": "new-jwt-token-here",
    "refreshToken": "new-refresh-token-here"
  }
  ```

## Project Structure
```
order-management-api/
├── src/
│   ├── main/
│   │   ├── java/com/example/practice_add/
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java
│   │   │   │   └── OrderController.java
│   │   │   ├── dto/
│   │   │   │   ├── JwtResponse.java
│   │   │   │   ├── LoginRequest.java
│   │   │   │   └── SignupRequest.java
│   │   │   ├── entity/
│   │   │   │   ├── Order.java
│   │   │   │   ├── Role.java
│   │   │   │   └── User.java
│   │   │   ├── repo/
│   │   │   │   ├── OrderRepository.java
│   │   │   │   ├── RoleRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   └── util/
│   │   │       ├── AuthTokenFilter.java
│   │   │       └── JwtUtils.java
│   │   └── resources/
│   │       └── application.properties
├── pom.xml
└── README.md
```

## Security Notes
- Ensure the JWT secret key is strong and kept confidential.
- Use HTTPS in production to secure API communications.
- Regularly update dependencies to address security vulnerabilities.

## Contributing
1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Commit your changes (`git commit -m "Add your feature"`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a pull request.

## License
This project is licensed under the MIT License.