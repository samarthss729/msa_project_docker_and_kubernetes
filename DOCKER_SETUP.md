# Job Portal Microservices - Docker Setup Guide

This guide will help you dockerize and deploy your Spring Boot microservices-based job portal application.

## Project Structure

The project consists of 7 microservices:

1. **eureka-server** (Port: 8761) - Service Discovery Server
2. **api-gateway** (Port: 8080) - API Gateway
3. **authorization-service** (Port: 8081) - Authentication & Authorization
4. **user-service** (Port: 8082) - User Management
5. **job-service** (Port: 8083) - Job Management
6. **admin-service** (Port: 8084) - Admin Operations
7. **frontend-service** (Port: 8085) - Frontend UI

## Prerequisites

- Docker Desktop installed and running
- Docker Hub account (username: samarth00729)
- Maven installed
- Java 17 installed

## Step 1: Login to Docker Hub

```bash
docker login
```
Enter your Docker Hub credentials when prompted.

## Step 2: Build and Push Docker Images

### Option A: Using the Automated Script (Recommended)

#### For Windows:
```cmd
build-and-push.bat
```

#### For Linux/Mac:
```bash
chmod +x build-and-push.sh
./build-and-push.sh
```

### Option B: Manual Build and Push

#### Build JAR files for each service:
```bash
# Navigate to each service directory and build
cd eureka-server
mvn clean package -DskipTests
cd ../api-gateway
mvn clean package -DskipTests
cd ../authorization-service
mvn clean package -DskipTests
cd ../user-service
mvn clean package -DskipTests
cd ../job-service
mvn clean package -DskipTests
cd ../admin-service
mvn clean package -DskipTests
cd ../frontend-service
mvn clean package -DskipTests
cd ..
```

#### Build Docker images:
```bash
# Build images for each service
docker build -t samarth00729/job-portal-eureka-server:latest ./eureka-server
docker build -t samarth00729/job-portal-api-gateway:latest ./api-gateway
docker build -t samarth00729/job-portal-authorization-service:latest ./authorization-service
docker build -t samarth00729/job-portal-user-service:latest ./user-service
docker build -t samarth00729/job-portal-job-service:latest ./job-service
docker build -t samarth00729/job-portal-admin-service:latest ./admin-service
docker build -t samarth00729/job-portal-frontend-service:latest ./frontend-service
```

#### Push images to Docker Hub:
```bash
# Push all images to Docker Hub
docker push samarth00729/job-portal-eureka-server:latest
docker push samarth00729/job-portal-api-gateway:latest
docker push samarth00729/job-portal-authorization-service:latest
docker push samarth00729/job-portal-user-service:latest
docker push samarth00729/job-portal-job-service:latest
docker push samarth00729/job-portal-admin-service:latest
docker push samarth00729/job-portal-frontend-service:latest
```

## Step 3: Deploy the Application

### Using Docker Compose (Recommended)

```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down
```

### Using Individual Docker Commands

```bash
# Start MySQL database
docker run -d --name mysql -e MYSQL_ROOT_PASSWORD=rootpassword -e MYSQL_DATABASE=job_portal -e MYSQL_USER=jobuser -e MYSQL_PASSWORD=jobpassword -p 3306:3306 mysql:8.0

# Start Eureka Server
docker run -d --name eureka-server -p 8761:8761 samarth00729/job-portal-eureka-server:latest

# Start API Gateway
docker run -d --name api-gateway -p 8080:8080 -e EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://localhost:8761/eureka/ samarth00729/job-portal-api-gateway:latest

# Start Authorization Service
docker run -d --name authorization-service -p 8081:8081 -e EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://localhost:8761/eureka/ -e SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/job_portal -e SPRING_DATASOURCE_USERNAME=jobuser -e SPRING_DATASOURCE_PASSWORD=jobpassword samarth00729/job-portal-authorization-service:latest

# Start User Service
docker run -d --name user-service -p 8082:8082 -e EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://localhost:8761/eureka/ -e SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/job_portal -e SPRING_DATASOURCE_USERNAME=jobuser -e SPRING_DATASOURCE_PASSWORD=jobpassword samarth00729/job-portal-user-service:latest

# Start Job Service
docker run -d --name job-service -p 8083:8083 -e EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://localhost:8761/eureka/ -e SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/job_portal -e SPRING_DATASOURCE_USERNAME=jobuser -e SPRING_DATASOURCE_PASSWORD=jobpassword samarth00729/job-portal-job-service:latest

# Start Admin Service
docker run -d --name admin-service -p 8084:8084 -e EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://localhost:8761/eureka/ -e SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/job_portal -e SPRING_DATASOURCE_USERNAME=jobuser -e SPRING_DATASOURCE_PASSWORD=jobpassword samarth00729/job-portal-admin-service:latest

# Start Frontend Service
docker run -d --name frontend-service -p 8085:8085 -e EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://localhost:8761/eureka/ samarth00729/job-portal-frontend-service:latest
```

## Step 4: Verify Deployment

### Check running containers:
```bash
docker ps
```

### Check service health:
- Eureka Server: http://localhost:8761
- API Gateway: http://localhost:8080
- Frontend Service: http://localhost:8085

### View logs for specific service:
```bash
docker logs <container-name>
```

## Docker Images Created

The following Docker images will be created and pushed to Docker Hub:

- `samarth00729/job-portal-eureka-server:latest`
- `samarth00729/job-portal-api-gateway:latest`
- `samarth00729/job-portal-authorization-service:latest`
- `samarth00729/job-portal-user-service:latest`
- `samarth00729/job-portal-job-service:latest`
- `samarth00729/job-portal-admin-service:latest`
- `samarth00729/job-portal-frontend-service:latest`

## Troubleshooting

### Common Issues:

1. **Port conflicts**: Make sure ports 3306, 8080-8085, and 8761 are not in use
2. **Docker not running**: Ensure Docker Desktop is running
3. **Build failures**: Check if Maven and Java 17 are properly installed
4. **Database connection issues**: Ensure MySQL container is running and accessible

### Useful Commands:

```bash
# Remove all containers
docker rm -f $(docker ps -aq)

# Remove all images
docker rmi -f $(docker images -q)

# Clean up Docker system
docker system prune -a

# View Docker Hub images
docker search samarth00729
```

## Environment Variables

The Docker Compose file includes the following environment variables:

- **Database**: MySQL with database `job_portal`
- **Eureka**: Service discovery on port 8761
- **JVM Options**: Optimized for container performance

## Next Steps

1. Set up CI/CD pipeline for automated builds
2. Implement health checks and monitoring
3. Add load balancing and scaling
4. Implement proper logging and monitoring solutions
5. Set up backup and recovery procedures

## Support

For issues or questions, please check the Docker logs and ensure all prerequisites are met.
