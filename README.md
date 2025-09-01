# Order Management API Postman Collection

This Postman collection is designed to test the Order Management API, a Spring Boot application with JWT-based authentication. The API allows users to manage orders with role-based access control (ROLE_USER and ROLE_ADMIN). The collection includes requests for user authentication (signup and signin) and order operations (create, read, update, delete).

## Prerequisites

1. **Postman**: Install [Postman](https://www.postman.com/downloads/) to import and run the collection.
2. **Running API**: Ensure the Order Management API is running locally or on a server. The default base URL is `http://localhost:8080`.
3. **Database**: The API requires a database (configured in the Spring Boot application) with roles (`ROLE_USER`, `ROLE_ADMIN`) pre-populated.
4. **JWT Configuration**: Ensure the `jwtSecret` and `jwtExpirationMs` are set in the application properties of the Spring Boot app.

## Setup Instructions

1. **Import the Postman Collection**:
    - Download the `OrderManagementAPI.postman_collection.json` file from the provided artifact.
    - Open Postman, click **Import**, and select the JSON file to load the collection.

2. **Set Up Environment Variables**:
    - In Postman, create a new environment or edit the existing one.
    - Add the following variables:
      | Key       | Initial Value              | Description                              |
      |-----------|----------------------------|------------------------------------------|
      | `baseUrl` | `http://localhost:8080`    | Base URL of the API                     |
      | `jwtToken`| (Leave empty)              | Stores JWT token after sign-in          |
      | `orderId` | `1`                        | ID of the order for update/delete tests |

3. **Database Setup**:
    - Ensure the database has the `ROLE_USER` and `ROLE_ADMIN` roles in the `roles` table.
    - The application uses Spring Security with BCrypt password encoding and JWT authentication.

## Using the Collection

The collection is organized into two folders: **Auth** and **Orders**.

### Auth Folder
Contains requests for user authentication:
- **Sign Up (User)**: Registers a new user with `ROLE_USER`.
    - Endpoint: `POST {{baseUrl}}/api/auth/signup`
    - Body: `{"username": "testuser", "email": "testuser@example.com", "password": "password123", "role": ["user"]}`
- **Sign Up (Admin)**: Registers a new user with `ROLE_ADMIN`.
    - Endpoint: `POST {{baseUrl}}/api/auth/signup`
    - Body: `{"username": "adminuser", "email": "admin@example.com", "password": "admin123", "role": ["admin"]}`
- **Sign In**: Authenticates a user and stores the JWT token in the `jwtToken` environment variable.
    - Endpoint: `POST {{baseUrl}}/api/auth/signin`
    - Body: `{"username": "testuser", "password": "password123"}`
    - **Note**: The response includes a JWT token, automatically stored via a Postman test script.

### Orders Folder
Contains requests for order management (require JWT authentication):
- **Get All Orders (Admin)**: Retrieves all orders (ROLE_ADMIN only).
    - Endpoint: `GET {{baseUrl}}/api/orders`
    - Headers: `Authorization: Bearer {{jwtToken}}`
- **Get My Orders**: Retrieves orders for the authenticated user (ROLE_USER or ROLE_ADMIN).
    - Endpoint: `GET {{baseUrl}}/api/orders/my`
    - Headers: `Authorization: Bearer {{jwtToken}}`
- **Create Order**: Creates a new order for the authenticated user.
    - Endpoint: `POST {{baseUrl}}/api/orders`
    - Headers: `Authorization: Bearer {{jwtToken}}`, `Content-Type: application/json`
    - Body: `{"productName": "Sample Product", "price": 29.99}`
- **Update Order**: Updates an existing order (requires ownership or ROLE_ADMIN).
    - Endpoint: `PUT {{baseUrl}}/api/orders/{{orderId}}`
    - Headers: `Authorization: Bearer {{jwtToken}}`, `Content-Type: application/json`
    - Body: `{"productName": "Updated Product", "price": 39.99}`
- **Delete Order**: Deletes an order (requires ownership or ROLE_ADMIN).
    - Endpoint: `DELETE {{baseUrl}}/api/orders/{{orderId}}`
    - Headers: `Authorization: Bearer {{jwtToken}}`

## Testing Workflow

1. **Sign Up**:
    - Run the "Sign Up (User)" or "Sign Up (Admin)" request to create a user.
    - Verify the response: `User registered successfully!`.

2. **Sign In**:
    - Run the "Sign In" request with the created user’s credentials.
    - The JWT token is automatically stored in the `jwtToken` variable.

3. **Test Order Endpoints**:
    - Run "Create Order" to create a new order.
    - Note the order ID from the response and update the `orderId` variable.
    - Test "Get My Orders" to view the authenticated user’s orders.
    - Test "Update Order" and "Delete Order" using the `orderId`.
    - For admin-specific tests, sign in as an admin user and run "Get All Orders".

## Notes
- **Authentication**: All order-related requests require a valid JWT token in the `Authorization` header (`Bearer {{jwtToken}}`).
- **Error Handling**: If a request fails (e.g., 401 Unauthorized), ensure the `jwtToken` is valid and the user has the required role.
- **Order ID**: Update the `orderId` variable manually after creating an order to test update/delete requests.
- **Security**: The API uses Spring Security with JWT and role-based authorization (`ROLE_USER`, `ROLE_ADMIN`).

## Troubleshooting
- **401 Unauthorized**: Verify the JWT token is valid and not expired. Re-run the "Sign In" request if needed.
- **403 Forbidden**: Ensure the user has the correct role (`ROLE_ADMIN` for "Get All Orders", ownership or `ROLE_ADMIN` for update/delete).
- **404 Not Found**: Check if the `orderId` exists in the database for update/delete requests.
- **Database Issues**: Ensure the database is running and roles are populated.

## Environment Variables
- Update `baseUrl` if the API is hosted on a different server.
- The `jwtToken` is set automatically after a successful sign-in.
- Set `orderId` manually after creating an order to test update/delete endpoints.

---