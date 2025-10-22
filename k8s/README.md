# Job Portal - Kubernetes Deployment Guide

This guide will help you deploy your Spring Boot microservices-based job portal application to Kubernetes using Docker Desktop's built-in Kubernetes.

## Prerequisites

- Docker Desktop with Kubernetes enabled
- kubectl command-line tool
- All Docker images pushed to Docker Hub (completed)

## Project Structure

The Kubernetes deployment consists of:

### Core Services:
- **MySQL Database** - Persistent storage
- **Eureka Server** - Service discovery
- **API Gateway** - Entry point for all requests
- **Authorization Service** - Authentication & authorization
- **User Service** - User management
- **Job Service** - Job management
- **Admin Service** - Admin operations
- **Frontend Service** - UI layer

### Kubernetes Resources:
- **Namespace**: `job-portal`
- **ConfigMap**: Application configuration
- **Deployments**: 8 microservices
- **Services**: Internal communication
- **Ingress**: External access
- **PVC**: Persistent storage for MySQL

## Quick Deployment

### Option 1: Automated Deployment (Recommended)

#### For Windows:
```cmd
k8s\deploy.bat
```

#### For Linux/Mac:
```bash
chmod +x k8s/deploy.sh
./k8s/deploy.sh
```

### Option 2: Manual Deployment

```bash
# 1. Create namespace
kubectl apply -f k8s/01-namespace.yaml

# 2. Create ConfigMap
kubectl apply -f k8s/02-configmap.yaml

# 3. Deploy MySQL
kubectl apply -f k8s/03-mysql.yaml

# 4. Wait for MySQL to be ready
kubectl wait --for=condition=ready pod -l app=mysql -n job-portal --timeout=300s

# 5. Deploy Eureka Server
kubectl apply -f k8s/04-eureka-server.yaml

# 6. Wait for Eureka Server
kubectl wait --for=condition=ready pod -l app=eureka-server -n job-portal --timeout=300s

# 7. Deploy all microservices
kubectl apply -f k8s/05-api-gateway.yaml
kubectl apply -f k8s/06-authorization-service.yaml
kubectl apply -f k8s/07-user-service.yaml
kubectl apply -f k8s/08-job-service.yaml
kubectl apply -f k8s/09-admin-service.yaml
kubectl apply -f k8s/10-frontend-service.yaml

# 8. Create Ingress
kubectl apply -f k8s/11-ingress.yaml
```

## Accessing the Application

### Method 1: Port Forwarding (Easiest)

```bash
# Access Frontend
kubectl port-forward -n job-portal service/frontend-service-service 8085:8085

# Access API Gateway
kubectl port-forward -n job-portal service/api-gateway-service 8080:8080

# Access Eureka Dashboard
kubectl port-forward -n job-portal service/eureka-server-service 8761:8761
```

Then access:
- Frontend: http://localhost:8085
- API Gateway: http://localhost:8080
- Eureka: http://localhost:8761

### Method 2: LoadBalancer Service

```bash
# Check LoadBalancer IP
kubectl get service job-portal-loadbalancer -n job-portal
```

### Method 3: Ingress (with hosts file)

1. Add to your hosts file (`C:\Windows\System32\drivers\etc\hosts` on Windows):
```
127.0.0.1 job-portal.local
```

2. Access via: http://job-portal.local

## Monitoring and Management

### Check Pod Status
```bash
kubectl get pods -n job-portal
```

### Check Services
```bash
kubectl get services -n job-portal
```

### Check Ingress
```bash
kubectl get ingress -n job-portal
```

### View Logs
```bash
# View logs for specific service
kubectl logs -f deployment/frontend-service-deployment -n job-portal

# View logs for all pods
kubectl logs -f -l app=frontend-service -n job-portal
```

### Scale Services
```bash
# Scale API Gateway to 3 replicas
kubectl scale deployment api-gateway-deployment --replicas=3 -n job-portal

# Scale User Service to 5 replicas
kubectl scale deployment user-service-deployment --replicas=5 -n job-portal
```

## Troubleshooting

### Common Issues:

1. **Pods not starting**:
   ```bash
   kubectl describe pod <pod-name> -n job-portal
   kubectl logs <pod-name> -n job-portal
   ```

2. **Services not accessible**:
   ```bash
   kubectl get endpoints -n job-portal
   kubectl describe service <service-name> -n job-portal
   ```

3. **Database connection issues**:
   ```bash
   kubectl logs deployment/mysql-deployment -n job-portal
   kubectl exec -it deployment/mysql-deployment -n job-portal -- mysql -u root -p
   ```

4. **Eureka registration issues**:
   ```bash
   kubectl logs deployment/eureka-server-deployment -n job-portal
   ```

### Useful Commands:

```bash
# Get all resources in namespace
kubectl get all -n job-portal

# Delete specific deployment
kubectl delete deployment <deployment-name> -n job-portal

# Restart deployment
kubectl rollout restart deployment/<deployment-name> -n job-portal

# Check resource usage
kubectl top pods -n job-portal
kubectl top nodes
```

## Cleanup

### Delete entire deployment:
```bash
kubectl delete namespace job-portal
```

### Delete specific resources:
```bash
kubectl delete -f k8s/11-ingress.yaml
kubectl delete -f k8s/10-frontend-service.yaml
# ... and so on
```

## Architecture Benefits

### Kubernetes Advantages:
- **High Availability**: Multiple replicas of each service
- **Auto-scaling**: Services can scale based on demand
- **Self-healing**: Failed pods are automatically restarted
- **Load Balancing**: Traffic distributed across replicas
- **Rolling Updates**: Zero-downtime deployments
- **Resource Management**: CPU and memory limits
- **Service Discovery**: Built-in DNS resolution
- **Persistent Storage**: MySQL data survives pod restarts

### Service Communication:
- **Internal**: Services communicate via Kubernetes DNS
- **External**: Access through Ingress or LoadBalancer
- **Database**: MySQL with persistent volume
- **Discovery**: Eureka Server for service registration

## Next Steps

1. **Monitoring**: Implement Prometheus and Grafana
2. **Logging**: Set up centralized logging with ELK stack
3. **CI/CD**: Automate deployments with GitHub Actions
4. **Security**: Implement network policies and RBAC
5. **Backup**: Set up database backup strategies
6. **Production**: Deploy to cloud Kubernetes (EKS, GKE, AKS)

## Support

For issues or questions:
1. Check pod logs: `kubectl logs <pod-name> -n job-portal`
2. Verify service status: `kubectl get pods -n job-portal`
3. Check resource usage: `kubectl top pods -n job-portal`
4. Review configuration: `kubectl describe configmap job-portal-config -n job-portal`
