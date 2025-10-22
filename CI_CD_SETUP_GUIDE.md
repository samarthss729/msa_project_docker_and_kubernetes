# CI/CD Pipeline Setup Guide for Job Portal Microservices

This guide will help you set up automated CI/CD pipelines using GitHub Actions to build and deploy your microservices to Docker Hub.

## Prerequisites

1. **GitHub Account**: samarthss729@gmail.com
2. **Docker Hub Account**: samarth00729
3. **Project Repository**: Already dockerized microservices project

## Step 1: Set Up GitHub Repository Secrets

### 1.1 Navigate to GitHub Repository Settings
1. Go to your GitHub repository: `https://github.com/samarthss729/msa_project_docker_and_kubernetes`
2. Click on **Settings** tab
3. In the left sidebar, click on **Secrets and variables** → **Actions**

### 1.2 Add Required Secrets
Click **New repository secret** and add the following secrets:

#### DOCKER_USERNAME
- **Name**: `DOCKER_USERNAME`
- **Value**: `samarth00729`

#### DOCKER_PASSWORD
- **Name**: `DOCKER_PASSWORD`
- **Value**: Your Docker Hub password or access token

> **Note**: For better security, use a Docker Hub access token instead of your password:
> 1. Go to Docker Hub → Account Settings → Security
> 2. Click "New Access Token"
> 3. Give it a name (e.g., "GitHub Actions")
> 4. Copy the token and use it as the DOCKER_PASSWORD secret

## Step 2: GitHub Actions Workflow

The CI/CD pipeline is already configured in `.github/workflows/ci-cd.yml`. This workflow will:

### Triggers
- **Push to main/master branch**: Builds and pushes all images
- **Pull requests**: Builds images for testing (doesn't push)

### What it does
1. **Builds each microservice** using Maven
2. **Creates Docker images** for all 7 services
3. **Pushes images to Docker Hub** with proper tags
4. **Uses caching** for faster builds
5. **Runs in parallel** for all services

### Services Built
- `samarth00729/job-portal-eureka-server:latest`
- `samarth00729/job-portal-api-gateway:latest`
- `samarth00729/job-portal-authorization-service:latest`
- `samarth00729/job-portal-user-service:latest`
- `samarth00729/job-portal-job-service:latest`
- `samarth00729/job-portal-admin-service:latest`
- `samarth00729/job-portal-frontend-service:latest`

## Step 3: Manual Setup Commands

### 3.1 Initialize Git Repository (if not already done)
```bash
# Navigate to your project directory
cd C:\Users\samar\OneDrive\Desktop\samarth_mca_sem_iii_projects_folder\msa_project_docker_and_kubernetes

# Initialize git (if not already done)
git init

# Add all files
git add .

# Commit initial changes
git commit -m "Initial commit: Microservices job portal with CI/CD setup"

# Add remote origin (replace with your actual repository URL)
git remote add origin https://github.com/samarthss729/msa_project_docker_and_kubernetes.git

# Push to GitHub
git push -u origin main
```

### 3.2 Verify GitHub Actions Setup
1. Go to your GitHub repository
2. Click on **Actions** tab
3. You should see the "CI/CD Pipeline for Job Portal Microservices" workflow
4. Click on it to see the workflow runs

## Step 4: Testing the Pipeline

### 4.1 Trigger a Build
Make a small change and push to trigger the pipeline:

```bash
# Make a small change (e.g., update README)
echo "# Updated by CI/CD pipeline" >> README.md

# Commit and push
git add README.md
git commit -m "Trigger CI/CD pipeline"
git push origin main
```

### 4.2 Monitor the Build
1. Go to GitHub Actions tab
2. Click on the latest workflow run
3. Watch the build progress for each service
4. Check that all services build successfully

## Step 5: Deploy Using Docker Compose

Once the images are built and pushed, you can deploy using:

```bash
# Pull latest images and start services
docker-compose pull
docker-compose up -d

# Check running containers
docker-compose ps

# View logs
docker-compose logs -f
```

## Step 6: Verify Deployment

### 6.1 Check Service Health
- **Eureka Server**: http://localhost:8761
- **API Gateway**: http://localhost:8090
- **Frontend Service**: http://localhost:8085

### 6.2 Test API Endpoints
```bash
# Test API Gateway
curl http://localhost:8090/api/jobs

# Test Eureka registration
curl http://localhost:8761/eureka/apps
```

## Advanced Configuration

### Custom Tags
The workflow automatically creates tags based on:
- **Branch name**: `main`, `develop`, etc.
- **Commit SHA**: `main-abc1234`
- **Latest**: `latest` (for main branch)

### Environment-Specific Deployments
You can modify the workflow to deploy to different environments:

```yaml
# Add environment-specific tags
tags: |
  type=ref,event=branch
  type=ref,event=pr
  type=sha,prefix={{branch}}-
  type=raw,value=latest,enable={{is_default_branch}}
  type=raw,value=staging,enable={{is_default_branch}}
```

## Troubleshooting

### Common Issues

1. **Build Failures**
   - Check Maven dependencies in `pom.xml`
   - Verify Java version compatibility
   - Check GitHub Actions logs for specific errors

2. **Docker Push Failures**
   - Verify Docker Hub credentials in secrets
   - Check if Docker Hub account has push permissions
   - Ensure repository names match exactly

3. **Service Registration Issues**
   - Verify Eureka Server is running first
   - Check network connectivity between services
   - Verify service discovery configuration

### Useful Commands

```bash
# Check GitHub Actions status
gh run list

# View workflow logs
gh run view <run-id>

# Test Docker images locally
docker run -d --name test-eureka samarth00729/job-portal-eureka-server:latest

# Clean up local images
docker system prune -a
```

## Security Best Practices

1. **Use Access Tokens**: Instead of passwords, use Docker Hub access tokens
2. **Non-root User**: Dockerfiles use non-root users for security
3. **Multi-stage Builds**: Reduce image size and attack surface
4. **Health Checks**: Monitor service health automatically
5. **Secrets Management**: Never commit credentials to code

## Next Steps

1. **Set up monitoring**: Add Prometheus/Grafana for service monitoring
2. **Implement blue-green deployment**: For zero-downtime deployments
3. **Add automated testing**: Unit tests, integration tests
4. **Set up staging environment**: Test deployments before production
5. **Implement rollback strategy**: Quick rollback in case of issues

## Support

For issues or questions:
1. Check GitHub Actions logs
2. Verify Docker Hub permissions
3. Test services locally first
4. Check network connectivity

---

**Happy Deploying! 🚀**
