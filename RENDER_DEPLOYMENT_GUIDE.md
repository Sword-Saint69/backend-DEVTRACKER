# 🚀 Deploy DevTracker Backend to Render

This guide walks you through deploying your Spring Boot backend to Render.

---

## 📋 Prerequisites

Before you begin, make sure you have:

1. ✅ A [Render account](https://render.com) (free tier available)
2. ✅ Your backend code pushed to a Git repository (GitHub, GitLab, or Bitbucket)
3. ✅ A **Neon PostgreSQL database** (or any PostgreSQL database)
   - If you don't have one, follow the [NEON_SETUP.md](backend/NEON_SETUP.md) guide

---

## 🔧 Step-by-Step Deployment

### Step 1: Prepare Your Repository

1. **Ensure your code is pushed to GitHub/GitLab/Bitbucket**
   ```bash
   git add .
   git commit -m "Prepare for Render deployment"
   git push origin main
   ```

2. **Verify these files exist in your backend directory:**
   - ✅ `Dockerfile` (already configured)
   - ✅ `render.yaml` (already configured)
   - ✅ `pom.xml` (Maven configuration)

---

### Step 2: Create a New Web Service on Render

1. **Go to [Render Dashboard](https://dashboard.render.com/)**

2. **Click "New +" → "Web Service"**

3. **Connect your Git repository:**
   - Click "Connect account" if you haven't linked GitHub/GitLab
   - Select your DevTracker repository
   - Click "Connect"

4. **Configure the service:**
   ```
   Name: devtracker-backend
   Region: Choose closest to your users (e.g., Oregon (US West))
   Branch: main (or your default branch)
   Root Directory: backend
   Environment: Docker
   Plan: Free (or choose paid for better performance)
   ```

---

### Step 3: Configure Environment Variables

Click **"Advanced"** and add these environment variables:

#### Required Variables:

| Key | Value | Description |
|-----|-------|-------------|
| `DB_URL` | `jdbc:postgresql://YOUR_NEON_ENDPOINT/neondb?sslmode=require` | Your Neon database URL |
| `DB_USERNAME` | `your_neon_username` | Your Neon database username |
| `DB_PASSWORD` | `your_neon_password` | Your Neon database password |
| `JWT_SECRET` | `your-secure-jwt-secret-at-least-32-characters` | Strong secret key for JWT |
| `CORS_ORIGINS` | `https://your-frontend-url.com,http://localhost:5173` | Allowed frontend URLs |

#### Optional Variables (with defaults):

| Key | Value | Description |
|-----|-------|-------------|
| `NEON_CONNECTION_TIMEOUT` | `30000` | Database connection timeout (ms) |
| `NEON_IDLE_TIMEOUT` | `600000` | Connection idle timeout (ms) |
| `NEON_MAX_LIFETIME` | `1800000` | Connection max lifetime (ms) |
| `SPRING_PROFILES_ACTIVE` | `prod` | Spring profile to use |

**Example Neon DB_URL:**
```
jdbc:postgresql://ep-gentle-poetry-a9m9sgoq-pooler.gwc.azure.neon.tech/neondb?sslmode=require
```

---

### Step 4: Health Check Configuration

Render will automatically use the health check path from `render.yaml`:

```yaml
healthCheckPath: /api/health
```

Make sure your backend has a health endpoint (recommended).

---

### Step 5: Deploy

1. **Click "Create Web Service"**

2. **Render will automatically:**
   - Clone your repository
   - Build the Docker image using your Dockerfile
   - Deploy the container
   - Assign a URL like `https://devtracker-backend.onrender.com`

3. **Monitor the deployment logs** in the Render dashboard
   - Look for "Build successful" and "Deploy live"
   - The first deployment takes ~5-10 minutes

---

## ✅ Verify Deployment

### Test Your API

1. **Get your Render URL:**
   - Found at the top of your service page
   - Example: `https://devtracker-backend.onrender.com`

2. **Test the health endpoint:**
   ```bash
   curl https://devtracker-backend.onrender.com/api/health
   ```

3. **Test a sample endpoint:**
   ```bash
   curl https://devtracker-backend.onrender.com/api/test
   ```

---

## 🔄 Automatic Deployments

Your `render.yaml` is configured with `autoDeploy: true`, which means:

- ✅ Every push to your `main` branch triggers automatic deployment
- ✅ No manual intervention needed
- ✅ Rollback available if something goes wrong

**To disable auto-deploy:**
- Go to your service settings → Disable "Auto-Deploy"

---

## 🛠 Common Issues & Solutions

### Issue 1: Build Fails - Maven Dependencies

**Error:** `Could not resolve dependencies`

**Solution:**
1. Clear Maven cache in your local project:
   ```bash
   mvn clean install -U
   ```
2. Push changes and redeploy

---

### Issue 2: Application Starts but Can't Connect to Database

**Error:** `Connection refused` or `Authentication failed`

**Solution:**
1. Verify environment variables in Render dashboard:
   - Check `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`
2. Ensure Neon database is active (not in sleep mode)
3. Check Neon connection pooler settings
4. Verify SSL mode is set to `require`

---

### Issue 3: CORS Errors from Frontend

**Error:** `Access-Control-Allow-Origin`

**Solution:**
1. Add your frontend URL to `CORS_ORIGINS` environment variable:
   ```
   CORS_ORIGINS=https://your-frontend.com,http://localhost:5173
   ```
2. Redeploy the service

---

### Issue 4: Health Check Failing

**Error:** `Health check failed`

**Solution:**
1. Ensure your Spring Boot app has a health endpoint:
   ```java
   @RestController
   @RequestMapping("/api")
   public class HealthController {
       @GetMapping("/health")
       public ResponseEntity<String> health() {
           return ResponseEntity.ok("OK");
       }
   }
   ```
2. Update `render.yaml` if your health endpoint is different

---

### Issue 5: Free Tier Spin Down

**Behavior:** Service goes to sleep after 15 minutes of inactivity

**Solution:**
- **Upgrade to paid plan** (starts at $7/month) for always-on service
- **Or use a keep-alive service** like [UptimeRobot](https://uptimerobot.com/) to ping your API every 5-10 minutes

---

## 📊 Monitoring & Logs

### View Logs:
1. Go to your service in Render dashboard
2. Click **"Logs"** tab
3. View real-time application logs

### Common Log Commands:
```bash
# Filter by error level
grep "ERROR" logs

# Check startup logs
grep "Started DevTrackerApplication" logs
```

---

## 🔐 Security Best Practices

1. ✅ **Never commit secrets** to your repository
   - Use Render environment variables for all sensitive data
   
2. ✅ **Use strong JWT secret**
   - Minimum 32 characters, random string
   - Generate with: `openssl rand -base64 32`

3. ✅ **Enable SSL for database connections**
   - Already configured in `application.properties`

4. ✅ **Limit CORS origins**
   - Only allow your actual frontend domains

5. ✅ **Keep dependencies updated**
   ```bash
   mvn versions:display-dependency-updates
   ```

---

## 💰 Cost Considerations

### Free Tier:
- ✅ 750 hours/month of runtime
- ✅ Automatic sleep after 15 min inactivity
- ✅ 512 MB RAM
- ✅ Shared CPU

### Starter Plan ($7/month):
- ✅ Always-on (no sleep)
- ✅ 512 MB RAM
- ✅ Shared CPU

### Standard Plan ($25/month):
- ✅ 2 GB RAM
- ✅ 1 CPU
- ✅ Better performance

---

## 🔗 Update Frontend Configuration

After deployment, update your frontend API base URL:

**In your frontend project (`frontend/devtracker-frontend/src/lib/api.ts`):**

```typescript
const API_BASE_URL = import.meta.env.PROD 
  ? 'https://devtracker-backend.onrender.com/api'
  : 'http://localhost:8080/api';

export default API_BASE_URL;
```

**Or set environment variable in frontend:**
```bash
VITE_API_URL=https://devtracker-backend.onrender.com/api
```

---

## 📝 Deployment Checklist

Before deploying, ensure:

- [ ] Code is pushed to Git repository
- [ ] Dockerfile exists and is valid
- [ ] render.yaml is configured
- [ ] Neon database is created and active
- [ ] All environment variables are ready
- [ ] Health endpoint is implemented
- [ ] CORS origins include your frontend URL
- [ ] JWT secret is secure (32+ characters)
- [ ] Database credentials are correct

---

## 🆘 Need Help?

- **Render Documentation:** https://render.com/docs
- **Spring Boot Deployment:** https://spring.io/guides/gs/spring-boot-docker/
- **Neon PostgreSQL:** https://neon.tech/docs/introduction

---

## 🎉 Success!

Once deployed, your backend will be available at:
```
https://devtracker-backend.onrender.com
```

You can now connect your frontend to this URL and have a fully deployed DevTracker application! 🚀

---

**Made with ❤️ for DevTracker**

*Last Updated: 2025-10-16*
