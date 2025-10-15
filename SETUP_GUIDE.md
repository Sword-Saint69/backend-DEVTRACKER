# DevTracker Setup Guide

## Prerequisites

1. Java 17+
2. Node.js 16+
3. Maven
4. PostgreSQL database (configured in the application)

## Initial Setup

1. **Start the backend server:**
   ```bash
   cd backend
   ./run-local.ps1
   ```

2. **Start the frontend:**
   ```bash
   cd frontend/devtracker-frontend
   npm install
   npm run dev
   ```

## User Authentication Flow

DevTracker requires users to be authenticated and part of an organization to access projects. Follow these steps:

### 1. Register a New User

Use the signup page or make a POST request to `/api/auth/signup`:

```json
{
  "userName": "Your Name",
  "email": "your.email@example.com",
  "password": "YourSecurePassword123!",
  "uuId": "unique-user-id-123",
  "position": "Developer"
}
```

### 2. Login

After registration, login to get an authentication token:

```json
{
  "email": "your.email@example.com",
  "password": "YourSecurePassword123!"
}
```

### 3. Create or Join an Organization

After logging in, you'll need to either:
- Create a new organization (if you're the first user)
- Join an existing organization (using an organization ID and passcode)

#### Create an Organization:
POST to `/organization/create` with:
```json
{
  "name": "Your Organization Name",
  "description": "Description of your organization",
  "ownerId": [your-user-id]
}
```

#### Join an Organization:
POST to `/organization/join` with:
```json
{
  "orgId": [organization-id],
  "passcode": "[organization-passcode]",
  "userId": [your-user-id]
}
```

## Troubleshooting

### "Failed to load projects" Error

This error typically occurs when:

1. **No authentication token**: Make sure you're logged in
2. **Invalid token**: Log out and log back in
3. **Not part of an organization**: Create or join an organization

### Common Solutions

1. **Clear browser data**: Clear localStorage and cookies for the site
2. **Check backend logs**: Look for any error messages in the backend console
3. **Verify database connection**: Ensure the database is accessible

## Test Script

Run this PowerShell script to set up a test user and organization:

```powershell
# test-setup.ps1
Write-Host "Setting up test user and organization..." -ForegroundColor Green

# Generate unique identifiers
$timestamp = Get-Date -Format "yyyyMMddHHmmss"
$userEmail = "testuser$timestamp@example.com"
$userPassword = "TestPass123!"
$userId = "test-uuid-$timestamp"
$orgName = "Test Organization $timestamp"

Write-Host "1. Registering user..." -ForegroundColor Yellow
# Register user (you'll need to implement this based on your frontend)

Write-Host "2. Logging in..." -ForegroundColor Yellow
# Login and get token (you'll need to implement this based on your frontend)

Write-Host "3. Creating organization..." -ForegroundColor Yellow
# Create organization (you'll need to implement this based on your frontend)

Write-Host "4. Joining organization..." -ForegroundColor Yellow
# Join organization (you'll need to implement this based on your frontend)

Write-Host "Setup complete!" -ForegroundColor Green
```

## API Endpoints

### Authentication
- `POST /api/auth/signup` - Register new user
- `POST /api/auth/login` - Login and get token

### Organizations
- `POST /organization/create` - Create new organization
- `POST /organization/join` - Join existing organization
- `GET /organization/{orgId}/passcode/{ownerId}` - Get organization passcode
- `POST /organization/{orgId}/regenerate/{ownerId}` - Regenerate passcode

### Projects
- `GET /project/all` - Get all projects for your organization
- `POST /project/add` - Create new project
- `GET /project/search?keyword={keyword}` - Search projects by name
- `PUT /project/update/{id}` - Update project
- `DELETE /project/delete/{id}` - Delete project

## Need Help?

If you're still experiencing issues:

1. Check that the backend server is running on port 8080
2. Verify database connectivity
3. Ensure CORS is properly configured
4. Check browser console for detailed error messages