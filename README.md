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

## Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- Postman (optional, for testing API endpoints)
- Git

## Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/order-management-api.git
cd order-management-api
```

### 2. Configure Application Properties
Edit `src/main/resources/application.properties` to configure the database and JWT settings:

```properties
# H2 Database configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true

# JWT configuration
app.jwt.secret=your-secure-jwt-secret-key
app.jwt.expiration-ms=86400000
```

Replace `your-secure-jwt-secret-key` with a secure key for JWT signing.

### 3. Build and Run the Application
```bash
mvn clean install
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

### 4. Access H2 Console (Optional)
If enabled, the H2 console is available at `http://localhost:8080/h2-console`. Use the JDBC URL `jdbc:h2:mem:testdb`, username `sa`, and no password.

## API Endpoints

### Authentication
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
  Authenticate a user and return a JWT token.  
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
    "token": "jwt-token-here"
  }
  ```

### Orders
All order endpoints require a valid JWT token in the `Authorization` header (`Bearer <token>`).

- **GET /api/orders**  
  Retrieve all orders (Admin only).  
  **Response**: List of all orders.

- **GET /api/orders/my**  
  Retrieve authenticated user's orders.  
  **Response**: List of user's orders.

- **POST /api/orders**  
  Create a new order for the authenticated user.  
  **Body**:
  ```json
  {
    "productName": "Sample Product",
    "price": 29.99
  }
  ```
  **Response**: Created order details.

- **PUT /api/orders/{id}**  
  Update an existing order (Admin or order owner).  
  **Body**:
  ```json
  {
    "productName": "Updated Product",
    "price": 39.99
  }
  ```
  **Response**: Updated order details.

- **DELETE /api/orders/{id}**  
  Delete an order (Admin or order owner).  
  **Response**: Empty response with status 200.

## Testing with Postman
1. Import the provided Postman collection (`OrderManagementAPI.postman_collection.json`) into Postman.
2. Set the `baseUrl` environment variable to `http://localhost:8080`.
3. Use the `Sign Up` and `Sign In` requests to register and authenticate users.
4. The `Sign In` request automatically stores the JWT token in the `jwtToken` environment variable.
5. Use the stored token for authenticated order requests.
6. Update the `orderId` environment variable for `Update Order` and `Delete Order` requests.

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