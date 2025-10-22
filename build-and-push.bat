@echo off
setlocal enabledelayedexpansion

REM Docker Hub username
set DOCKER_USERNAME=samarth00729

REM Array of microservices
set SERVICES=eureka-server api-gateway authorization-service user-service job-service admin-service frontend-service

echo Starting Docker build and push process for Job Portal Microservices...

REM Check if Docker is running
docker info >nul 2>&1
if errorlevel 1 (
    echo Error: Docker is not running. Please start Docker and try again.
    pause
    exit /b 1
)

REM Login to Docker Hub
echo Please make sure you are logged in to Docker Hub with: docker login
echo Press any key to continue...
pause >nul

REM Build and push each service
for %%s in (%SERVICES%) do (
    echo ==========================================
    echo Building Docker image for %%s...
    echo ==========================================
    
    REM Navigate to service directory
    cd %%s
    
    REM Build the JAR file using Maven wrapper
    echo Building JAR file for %%s...
    call ..\mvnw.cmd clean package -DskipTests
    
    if errorlevel 1 (
        echo Error: Failed to build JAR for %%s
        pause
        exit /b 1
    )
    
    REM Build Docker image
    set IMAGE_NAME=job-portal-%%s
    echo Building Docker image: %DOCKER_USERNAME%/!IMAGE_NAME!:latest
    docker build -t %DOCKER_USERNAME%/!IMAGE_NAME!:latest .
    
    if errorlevel 1 (
        echo Error: Failed to build Docker image for %%s
        pause
        exit /b 1
    )
    
    REM Push Docker image to Docker Hub
    echo Pushing Docker image to Docker Hub...
    docker push %DOCKER_USERNAME%/!IMAGE_NAME!:latest
    
    if errorlevel 1 (
        echo Error: Failed to push Docker image for %%s
        pause
        exit /b 1
    )
    
    echo Successfully built and pushed %%s
    
    REM Navigate back to root directory
    cd ..
)

echo ==========================================
echo All microservices have been built and pushed successfully!
echo ==========================================
echo.
echo You can now run the application using:
echo docker-compose up -d
echo.
echo Or run individual services:
for %%s in (%SERVICES%) do (
    echo docker run -d --name %%s samarth00729/job-portal-%%s:latest
)

pause
