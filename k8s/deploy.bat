@echo off
setlocal enabledelayedexpansion

echo ==========================================
echo Deploying Job Portal to Kubernetes
echo ==========================================

REM Check if kubectl is available
kubectl version --client >nul 2>&1
if errorlevel 1 (
    echo Error: kubectl is not installed or not in PATH
    pause
    exit /b 1
)

REM Check if Kubernetes is running
kubectl cluster-info >nul 2>&1
if errorlevel 1 (
    echo Error: Kubernetes cluster is not running
    echo Please enable Kubernetes in Docker Desktop
    pause
    exit /b 1
)

echo Kubernetes cluster is running...

REM Apply all Kubernetes manifests in order
echo Creating namespace...
kubectl apply -f k8s\01-namespace.yaml

echo Creating ConfigMap...
kubectl apply -f k8s\02-configmap.yaml

echo Deploying MySQL...
kubectl apply -f k8s\03-mysql.yaml

echo Waiting for MySQL to be ready...
kubectl wait --for=condition=ready pod -l app=mysql -n job-portal --timeout=300s

echo Deploying Eureka Server...
kubectl apply -f k8s\04-eureka-server.yaml

echo Waiting for Eureka Server to be ready...
kubectl wait --for=condition=ready pod -l app=eureka-server -n job-portal --timeout=300s

echo Deploying API Gateway...
kubectl apply -f k8s\05-api-gateway.yaml

echo Deploying Authorization Service...
kubectl apply -f k8s\06-authorization-service.yaml

echo Deploying User Service...
kubectl apply -f k8s\07-user-service.yaml

echo Deploying Job Service...
kubectl apply -f k8s\08-job-service.yaml

echo Deploying Admin Service...
kubectl apply -f k8s\09-admin-service.yaml

echo Deploying Frontend Service...
kubectl apply -f k8s\10-frontend-service.yaml

echo Creating Ingress...
kubectl apply -f k8s\11-ingress.yaml

echo ==========================================
echo Deployment completed!
echo ==========================================

echo Checking pod status...
kubectl get pods -n job-portal

echo.
echo Checking services...
kubectl get services -n job-portal

echo.
echo Checking ingress...
kubectl get ingress -n job-portal

echo.
echo To access the application:
echo 1. Add '127.0.0.1 job-portal.local' to your hosts file
echo 2. Access via: http://job-portal.local
echo 3. Or use port forwarding: kubectl port-forward -n job-portal service/frontend-service-service 8085:8085
echo.
echo To view logs: kubectl logs -f deployment/frontend-service-deployment -n job-portal
echo To delete deployment: kubectl delete namespace job-portal

pause
