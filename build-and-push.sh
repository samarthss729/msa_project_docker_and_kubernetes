#!/bin/bash

# Docker Hub username
DOCKER_USERNAME="samarth00729"

# Array of microservices
SERVICES=("eureka-server" "api-gateway" "authorization-service" "user-service" "job-service" "admin-service" "frontend-service")

echo "Starting Docker build and push process for Job Portal Microservices..."

# Function to build and push Docker image for a service
build_and_push() {
    local service=$1
    local image_name="job-portal-${service}"
    
    echo "=========================================="
    echo "Building Docker image for $service..."
    echo "=========================================="
    
    # Navigate to service directory
    cd "$service"
    
    # Build the JAR file using Maven
    echo "Building JAR file for $service..."
    mvn clean package -DskipTests
    
    if [ $? -ne 0 ]; then
        echo "Error: Failed to build JAR for $service"
        exit 1
    fi
    
    # Build Docker image
    echo "Building Docker image: $DOCKER_USERNAME/$image_name:latest"
    docker build -t "$DOCKER_USERNAME/$image_name:latest" .
    
    if [ $? -ne 0 ]; then
        echo "Error: Failed to build Docker image for $service"
        exit 1
    fi
    
    # Push Docker image to Docker Hub
    echo "Pushing Docker image to Docker Hub..."
    docker push "$DOCKER_USERNAME/$image_name:latest"
    
    if [ $? -ne 0 ]; then
        echo "Error: Failed to push Docker image for $service"
        exit 1
    fi
    
    echo "Successfully built and pushed $service"
    
    # Navigate back to root directory
    cd ..
}

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "Error: Docker is not running. Please start Docker and try again."
    exit 1
fi

# Login to Docker Hub (optional - you can also login manually)
echo "Please make sure you are logged in to Docker Hub with: docker login"
echo "Press Enter to continue..."
read

# Build and push each service
for service in "${SERVICES[@]}"; do
    build_and_push "$service"
done

echo "=========================================="
echo "All microservices have been built and pushed successfully!"
echo "=========================================="
echo ""
echo "You can now run the application using:"
echo "docker-compose up -d"
echo ""
echo "Or run individual services:"
for service in "${SERVICES[@]}"; do
    echo "docker run -d --name $service samarth00729/job-portal-$service:latest"
done
