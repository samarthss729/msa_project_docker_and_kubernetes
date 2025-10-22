#!/bin/bash

echo "=========================================="
echo "GitHub Repository Setup Script"
echo "=========================================="
echo

# Check if git is installed
if ! command -v git &> /dev/null; then
    echo "Error: Git is not installed or not in PATH"
    echo "Please install Git from https://git-scm.com/"
    exit 1
fi

echo "Git is installed. Proceeding with setup..."
echo

# Initialize git repository if not already done
if [ ! -d ".git" ]; then
    echo "Initializing Git repository..."
    git init
    echo "Git repository initialized."
else
    echo "Git repository already exists."
fi

echo

# Add all files to git
echo "Adding all files to Git..."
git add .
echo "Files added to Git."

echo

# Check if there are any changes to commit
if git diff --cached --quiet; then
    echo "No changes to commit."
else
    echo "Committing changes..."
    git commit -m "Initial commit: Microservices job portal with CI/CD setup"
    echo "Changes committed."
fi

echo

# Set up remote origin
echo "Setting up remote origin..."
git remote remove origin 2>/dev/null || true
git remote add origin https://github.com/samarthss729/msa_project_docker_and_kubernetes.git
echo "Remote origin set to: https://github.com/samarthss729/msa_project_docker_and_kubernetes.git"

echo

# Push to GitHub
echo "Pushing to GitHub..."
git branch -M main
git push -u origin main

if [ $? -eq 0 ]; then
    echo
    echo "=========================================="
    echo "SUCCESS! Repository setup completed."
    echo "=========================================="
    echo
    echo "Next steps:"
    echo "1. Go to GitHub repository settings"
    echo "2. Add Docker Hub secrets (DOCKER_USERNAME and DOCKER_PASSWORD)"
    echo "3. Check the Actions tab for CI/CD pipeline"
    echo "4. Make a small change and push to trigger the pipeline"
    echo
    echo "Repository URL: https://github.com/samarthss729/msa_project_docker_and_kubernetes"
    echo "Actions URL: https://github.com/samarthss729/msa_project_docker_and_kubernetes/actions"
    echo
else
    echo
    echo "=========================================="
    echo "ERROR: Failed to push to GitHub"
    echo "=========================================="
    echo
    echo "Possible solutions:"
    echo "1. Check your GitHub credentials"
    echo "2. Verify the repository exists and you have push access"
    echo "3. Check your internet connection"
    echo "4. Try running: git push -u origin main"
    echo
fi

echo "Press Enter to exit..."
read
