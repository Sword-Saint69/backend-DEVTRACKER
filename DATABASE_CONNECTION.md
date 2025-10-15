# Database Connection Setup

## ✅ Successfully Connected to Neon PostgreSQL

### Connection Details
- **Database**: `neondb`
- **Host**: `ep-gentle-poetry-a9m9sgoq-pooler.gwc.azure.neon.tech`
- **Region**: Azure GWC (Global West Central)
- **PostgreSQL Version**: 17.5
- **SSL Mode**: Required with channel binding

### Configuration Files Updated

#### 1. `application-dev.properties`
Located at: `backend/src/main/resources/application-dev.properties`

Updated with the new Neon database credentials:
```properties
spring.datasource.url=jdbc:postgresql://ep-gentle-poetry-a9m9sgoq-pooler.gwc.azure.neon.tech/neondb?sslmode=require&channel_binding=require
spring.datasource.username=neondb_owner
spring.datasource.password=npg_wE1LrmuCtU4y
```

#### 2. `.env` File Created
Located at: `backend/.env`

Environment variables for easy configuration management (already in `.gitignore`):
```bash
DB_URL=jdbc:postgresql://ep-gentle-poetry-a9m9sgoq-pooler.gwc.azure.neon.tech/neondb?sslmode=require&channel_binding=require
DB_USERNAME=neondb_owner
DB_PASSWORD=npg_wE1LrmuCtU4y
```

### Database Tables Created ✅

The application successfully created all required tables:
- ✅ `user` - User accounts
- ✅ `organization` - Organizations
- ✅ `project` - Projects
- ✅ `project_members` - Project team members
- ✅ `task` - Tasks/Issues
- ✅ `comment` - Task comments
- ✅ `message` - Messages

### How to Run

#### Development Mode (with dev profile)
```bash
cd backend
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

#### Production Mode (with environment variables)
```bash
cd backend
export $(cat .env | xargs)
./mvnw spring-boot:run
```

### Application Status
- **Status**: ✅ Running
- **Port**: 8080
- **Profile**: dev
- **H2 Console**: Available at `/h2-console`
- **WebSocket**: Enabled (SimpleBroker running)

### Connection Pool Configuration
- **Maximum Pool Size**: 10
- **Minimum Idle**: 2
- **Connection Timeout**: 30 seconds
- **Idle Timeout**: 10 minutes
- **Max Lifetime**: 30 minutes

### Security
- ✅ `.env` file is in `.gitignore` (credentials won't be committed)
- ✅ SSL/TLS encryption enabled
- ✅ Channel binding required for enhanced security

### Direct psql Connection
If you need to connect directly to the database using psql:
```bash
psql 'postgresql://neondb_owner:npg_wE1LrmuCtU4y@ep-gentle-poetry-a9m9sgoq-pooler.gwc.azure.neon.tech/neondb?sslmode=require&channel_binding=require'
```

---
**Last Updated**: October 15, 2025
**Database Provider**: Neon (Serverless PostgreSQL)
