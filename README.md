# Microservices Architecture Project

This project has been transformed from a monolithic Spring Boot application into a microservices architecture with the following components:

## Architecture Overview

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   API Gateway   │    │  Eureka Server  │    │ Authorization   │
│   (Port: 8085)  │    │  (Port: 8761)   │    │   Service       │
│                 │    │                 │    │  (Port: 8081)   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         └───────────────────────┼───────────────────────┘
                                 │
         ┌───────────────────────┼───────────────────────┐
         │                       │                       │
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│  User Service   │    │  Job Service    │    │ Admin Service   │
│  (Port: 8082)   │    │  (Port: 8083)   │    │  (Port: 8084)   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## Services

### 1. Eureka Server (Service Discovery)
- **Port**: 8761
- **Purpose**: Service registry and discovery
- **URL**: http://localhost:8761

### 2. API Gateway
- **Port**: 8085
- **Purpose**: Central entry point for all microservices
- **Features**: 
  - Routing to microservices
  - Authentication filtering
  - Rate limiting
  - CORS configuration

### 3. Authorization Service
- **Port**: 8081
- **Purpose**: User authentication and authorization
- **Endpoints**:
  - `POST /auth/login` - User login
  - `POST /auth/register` - User registration
  - `GET /auth/validate/{username}` - Validate user

### 4. User Service
- **Port**: 8082
- **Purpose**: User management operations
- **Endpoints**:
  - `GET /users` - Get all users
  - `GET /users/{id}` - Get user by ID
  - `POST /users` - Create user
  - `PUT /users/{id}` - Update user
  - `DELETE /users/{id}` - Delete user

### 5. Job Service
- **Port**: 8083
- **Purpose**: Job listing and details management
- **Endpoints**:
  - `GET /jobs` - Get all job listings
  - `GET /jobs/{id}` - Get job details by ID

### 6. Admin Service
- **Port**: 8084
- **Purpose**: Administrative operations
- **Endpoints**:
  - `POST /admin/login` - Admin login
  - `POST /admin/logout` - Admin logout

## How to Run in IntelliJ IDEA

### Prerequisites
1. Java 17 or higher
2. Maven 3.6+
3. MySQL database running on localhost:3306
4. Database: `msa_project_database` with user `root` and password `Vbh*132651`

### Running the Services

#### Method 1: Using Run Configurations (Recommended)
1. Open IntelliJ IDEA
2. Import the project as a Maven project
3. Wait for Maven to download dependencies
4. Use the pre-configured run configurations:
   - **Eureka Server** (Run this first)
   - **API Gateway** (Run this second)
   - **Authorization Service**
   - **User Service**
   - **Job Service**
   - **Admin Service**

#### Method 2: Using Maven Commands
```bash
# Terminal 1 - Eureka Server
cd eureka-server
mvn spring-boot:run

# Terminal 2 - API Gateway
cd api-gateway
mvn spring-boot:run

# Terminal 3 - Authorization Service
cd authorization-service
mvn spring-boot:run

# Terminal 4 - User Service
cd user-service
mvn spring-boot:run

# Terminal 5 - Job Service
cd job-service
mvn spring-boot:run

# Terminal 6 - Admin Service
cd admin-service
mvn spring-boot:run
```

### Starting Order
1. **Eureka Server** (Port 8761) - Must start first
2. **API Gateway** (Port 8085) - Start second
3. **All other services** - Can start in any order

## API Endpoints

All API calls should be made through the API Gateway at `http://localhost:8085`:

### Authentication
- `POST http://localhost:8085/api/auth/login`
- `POST http://localhost:8085/api/auth/register`

### Users
- `GET http://localhost:8085/api/users`
- `GET http://localhost:8085/api/users/{id}`
- `POST http://localhost:8085/api/users`
- `PUT http://localhost:8085/api/users/{id}`
- `DELETE http://localhost:8085/api/users/{id}`

### Jobs
- `GET http://localhost:8085/api/jobs`
- `GET http://localhost:8085/api/jobs/{id}`

### Admin
- `POST http://localhost:8085/api/admin/login`
- `POST http://localhost:8085/api/admin/logout`

## Service Discovery

- **Eureka Dashboard**: http://localhost:8761
- All services will register themselves with Eureka Server
- API Gateway uses service discovery to route requests

## Database Configuration

All services use the same MySQL database:
- **Host**: localhost:3306
- **Database**: msa_project_database
- **Username**: root
- **Password**: Vbh*132651

## Troubleshooting

1. **Services not registering with Eureka**: Ensure Eureka Server is running first
2. **API Gateway routing issues**: Check if target services are registered in Eureka
3. **Database connection issues**: Verify MySQL is running and credentials are correct
4. **Port conflicts**: Ensure no other applications are using the configured ports

## Development Notes

- Each service is independently deployable
- Services communicate through HTTP REST APIs
- Service discovery is handled by Eureka
- API Gateway provides centralized routing and cross-cutting concerns
- All services use the same database (in production, consider separate databases)


"# CI/CD Pipeline Active" 
