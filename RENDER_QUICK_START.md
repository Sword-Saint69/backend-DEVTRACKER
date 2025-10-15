# 🚀 Quick Start: Deploy to Render (5 Minutes)

Follow these steps to deploy your DevTracker backend to Render quickly.

---

## ⚡ Prerequisites

- [ ] GitHub/GitLab account with your code pushed
- [ ] Render account (sign up at [render.com](https://render.com))
- [ ] Neon PostgreSQL database credentials

---

## 📝 Step 1: Push Your Code (1 min)

```bash
cd /Users/gouthamsankar/Desktop/Devtracker
git add .
git commit -m "Ready for Render deployment"
git push origin main
```

---

## 🔗 Step 2: Create Web Service on Render (2 min)

1. Go to [Render Dashboard](https://dashboard.render.com/)
2. Click **"New +"** → **"Web Service"**
3. Connect your GitHub repository
4. Fill in:
   - **Name:** `devtracker-backend`
   - **Root Directory:** `backend`
   - **Environment:** Docker
   - **Plan:** Free

---

## 🔑 Step 3: Add Environment Variables (2 min)

Click **"Advanced"** and add these variables:

### Required:
```plaintext
DB_URL=jdbc:postgresql://YOUR-NEON-ENDPOINT.neon.tech/neondb?sslmode=require
DB_USERNAME=your_neon_username
DB_PASSWORD=your_neon_password
JWT_SECRET=generate-a-random-32-character-secret-key-here
CORS_ORIGINS=http://localhost:5173,https://your-frontend.com
```

### Get Your Neon Credentials:
1. Go to [Neon Console](https://console.neon.tech/)
2. Select your project
3. Copy connection string from dashboard
4. Format as shown above

### Generate JWT Secret:
```bash
# Mac/Linux
openssl rand -base64 32

# Or use online generator
# https://randomkeygen.com/ (use "CodeIgniter Encryption Keys")
```

---

## 🎯 Step 4: Deploy! (Click Button)

1. Click **"Create Web Service"**
2. Wait ~5-10 minutes for first deployment
3. Your backend will be live at: `https://devtracker-backend.onrender.com`

---

## ✅ Step 5: Test Your API

```bash
# Test health endpoint
curl https://devtracker-backend.onrender.com/health/ping

# Expected response: "OK"
```

---

## 🔄 Auto-Deploy Setup

✅ **Already configured!** Every push to `main` branch will auto-deploy.

To disable: Go to Settings → Uncheck "Auto-Deploy"

---

## ⚠️ Important Notes

### Free Tier Limitations:
- Sleeps after 15 minutes of inactivity
- Takes ~30 seconds to wake up on first request
- 750 hours/month free usage

### Wake-Up Solutions:
1. **Upgrade to Starter plan** ($7/month) - always on
2. **Use UptimeRobot** (free) to ping every 5 minutes
3. **Accept the delay** (fine for development/demos)

---

## 🔗 Update Your Frontend

After deployment, update your frontend to use the new backend URL:

**File:** `frontend/devtracker-frontend/src/lib/api.ts`

```typescript
const API_BASE_URL = import.meta.env.VITE_API_URL || 
  (import.meta.env.PROD 
    ? 'https://devtracker-backend.onrender.com'
    : 'http://localhost:8080');
```

---

## 🐛 Troubleshooting

### Build Fails?
- Check logs in Render dashboard
- Ensure `Dockerfile` and `pom.xml` are in `/backend` directory

### Can't Connect to Database?
- Verify `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` in environment variables
- Check Neon database is active (not suspended)

### CORS Errors?
- Add your frontend URL to `CORS_ORIGINS`
- Format: `https://your-app.com,http://localhost:5173`

### Health Check Failing?
- Endpoint is `/health/ping`
- Should return "OK"
- Check application logs

---

## 📖 Full Documentation

For detailed setup, troubleshooting, and advanced configuration:
- See [RENDER_DEPLOYMENT_GUIDE.md](RENDER_DEPLOYMENT_GUIDE.md)

---

## 🎉 Done!

Your backend is now live and ready to serve your frontend application!

**Next Steps:**
1. Deploy your frontend (Vercel/Netlify)
2. Update frontend API URL
3. Test the full application

---

**Need Help?** Check the full guide or Render documentation at https://render.com/docs
