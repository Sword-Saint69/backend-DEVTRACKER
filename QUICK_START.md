# DevTracker Quick Start Guide

## Problem
You're seeing the error "failed to load project please try again" because the application requires proper authentication and organization setup.

## Solution

### Option 1: Use the Built-in Setup Helper (Recommended)

1. When you see the "Failed to load projects" error message, click the "Setup Account" button
2. Follow the guided steps to:
   - Create a new user account
   - Log in to get an authentication token
   - Create an organization
   - Automatically join the organization
3. The application will automatically refresh and show your projects

### Option 2: Manual Setup

1. **Register a new user:**
   - Go to the signup page or make a POST request to `/api/auth/signup`
   - Provide the required user information

2. **Log in:**
   - Go to the login page or make a POST request to `/api/auth/login`
   - Use your email and password
   - Save the JWT token from the response

3. **Create an organization:**
   - Make a POST request to `/organization/create` with your token
   - Provide organization details

4. **Join the organization:**
   - Make a POST request to `/organization/join` with your token
   - Provide the organization ID and passcode

### Option 3: Check Existing Setup

If you believe you already have an account:

1. Clear your browser's localStorage:
   ```javascript
   localStorage.clear();
   ```
2. Log in again through the normal login process
3. Make sure you're joining an organization after login

## Common Issues

### "Authentication failed" Error
- Your token may have expired
- Log out and log back in to get a new token

### "User must join an organization" Message
- After logging in, you need to create or join an organization
- Use the setup helper or follow the manual steps above

## Need More Help?

1. Check the detailed [SETUP_GUIDE.md](SETUP_GUIDE.md) for comprehensive instructions
2. Look at the browser console for detailed error messages
3. Check the backend server logs for any errors