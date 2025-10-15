# DevTracker Project - Complete Technical Overview

## Project Structure
The project follows a standard full-stack architecture with separate backend and frontend directories:
- **Backend**: Java Spring Boot application
- **Frontend**: React.js + TypeScript application

## Backend (Java Spring Boot)

### Frameworks & Libraries
1. **Spring Boot 3.5.3** - Main application framework
2. **Spring Data JPA** - Database access and ORM
3. **Spring Web** - RESTful API development
4. **Spring Security** - Authentication and authorization
5. **Spring WebSocket** - Real-time messaging
6. **JWT (JSON Web Token)** - Token-based authentication (jjwt 0.11.5)
7. **Lombok** - Code generation utilities
8. **PostgreSQL** - Primary database driver

### Modules & Components
1. **Main Application**: [DevTrackerApplication.java](file:///c%3A/Users/GOOD/Music/Devtracker/backend/src/main/java/com/devtracker/DevTracker/DevTrackerApplication.java)
2. **Controllers**:
   - [AuthController.java](file:///c%3A/Users/GOOD/Music/Devtracker/backend/src/main/java/com/devtracker/DevTracker/controller/AuthController.java) - Authentication endpoints
   - [ChatController.java](file:///c%3A/Users/GOOD/Music/Devtracker/backend/src/main/java/com/devtracker/DevTracker/controller/ChatController.java) - Chat functionality
   - [CommentController.java](file:///c%3A/Users/GOOD/Music/Devtracker/backend/src/main/java/com/devtracker/DevTracker/controller/CommentController.java) - Comment management
   - [HealthController.java](file:///c%3A/Users/GOOD/Music/Devtracker/backend/src/main/java/com/devtracker/DevTracker/controller/HealthController.java) - Health checks
   - [MessageController.java](file:///c%3A/Users/GOOD/Music/Devtracker/backend/src/main/java/com/devtracker/DevTracker/controller/MessageController.java) - Message handling
   - [OrganizationController.java](file:///c%3A/Users/GOOD/Music/Devtracker/backend/src/main/java/com/devtracker/DevTracker/controller/OrganizationController.java) - Organization management
   - [ProjectController.java](file:///c%3A/Users/GOOD/Music/Devtracker/backend/src/main/java/com/devtracker/DevTracker/controller/ProjectController.java) - Project operations
   - [TaskController.java](file:///c%3A/Users/GOOD/Music/Devtracker/backend/src/main/java/com/devtracker/DevTracker/controller/TaskController.java) - Task management
   - [UserController.java](file:///c%3A/Users/GOOD/Music/Devtracker/backend/src/main/java/com/devtracker/DevTracker/controller/UserController.java) - User operations

3. **Models**:
   - [Comment.java](file:///c%3A/Users/GOOD/Music/Devtracker/backend/src/main/java/com/devtracker/DevTracker/model/Comment.java)
   - [Message.java](file:///c%3A/Users/GOOD/Music/Devtracker/backend/src/main/java/com/devtracker/DevTracker/model/Message.java)
   - [Organization.java](file:///c%3A/Users/GOOD/Music/Devtracker/backend/src/main/java/com/devtracker/DevTracker/model/Organization.java)
   - [Project.java](file:///c%3A/Users/GOOD/Music/Devtracker/backend/src/main/java/com/devtracker/DevTracker/model/Project.java)
   - [Task.java](file:///c%3A/Users/GOOD/Music/Devtracker/backend/src/main/java/com/devtracker/DevTracker/model/Task.java)
   - [User.java](file:///c%3A/Users/GOOD/Music/Devtracker/backend/src/main/java/com/devtracker/DevTracker/model/User.java)
   - Enums in [enums](file:///c%3A/Users/GOOD/Music/Devtracker/backend/src/main/java/com/devtracker/DevTracker/model/enums/) directory

4. **DTOs (Data Transfer Objects)**:
   - Auth, Comment, Details, Message, Organization, Project, Task, and User packages

5. **Repositories**:
   - Interfaces for database operations for each model

6. **Services**:
   - Business logic implementation for each module

7. **Security Configuration**:
   - [JwtAuthFilter.java](file:///c%3A/Users/GOOD/Music/Devtracker/backend/src/main/java/com/devtracker/DevTracker/config/JwtAuthFilter.java) - JWT authentication filter
   - [JwtUtil.java](file:///c%3A/Users/GOOD/Music/Devtracker/backend/src/main/java/com/devtracker/DevTracker/config/JwtUtil.java) - JWT utility functions
   - [SecurityConfig.java](file:///c%3A/Users/GOOD/Music/Devtracker/backend/src/main/java/com/devtracker/DevTracker/config/SecurityConfig.java) - Security configuration
   - [PasswordConfig.java](file:///c%3A/Users/GOOD/Music/Devtracker/backend/src/main/java/com/devtracker/DevTracker/config/PasswordConfig.java) - Password encoding

8. **WebSocket Configuration**:
   - [WebSocketConfig.java](file:///c%3A/Users/GOOD/Music/Devtracker/backend/src/main/java/com/devtracker/DevTracker/config/WebSocketConfig.java)
   - [WebSocketEventListener.java](file:///c%3A/Users/GOOD/Music/Devtracker/backend/src/main/java/com/devtracker/DevTracker/config/WebSocketEventListener.java)

9. **Mappers**:
   - Conversion between entities and DTOs

### Database
- **Primary Database**: Neon PostgreSQL (Serverless)
- **Connection Pooling**: HikariCP with Neon-specific optimizations
- **SSL Configuration**: Required for Neon connections
- **IMPORTANT**: The project MUST use Neon PostgreSQL as the sole database backend for authentication and all data storage. H2 or other embedded databases should NOT be used in any environment.

## Frontend (React.js + TypeScript)

### Frameworks & Libraries
1. **React 19.1.1** - Core UI library
2. **TypeScript** - Type checking
3. **React Router 7.8.2** - Client-side routing
4. **Axios** - HTTP client for API requests
5. **Tailwind CSS** - Utility-first CSS framework
6. **Bootstrap 5.3.8** - UI component library
7. **@hello-pangea/dnd** - Drag and drop functionality
8. **@tanstack/react-query** - Server state management
9. **Chart.js + react-chartjs-2** - Data visualization
10. **JWT-decode** - Decoding JWT tokens
11. **STOMP.js** - WebSocket communication
12. **Lucide React** - Icon library
13. **Framer Motion** - Animation library
14. **@tabler/icons-react** - Additional icon library
15. **Radix UI** - Accessible UI primitives
16. **Class Variance Authority** - Utility for managing component variants
17. **clsx + tailwind-merge** - Utility for constructing className strings

### UI Libraries & Components

#### Custom UI Components
The project includes a set of custom UI components in the [src/components/ui](file:///c%3A/Users/GOOD/Music/Devtracker/frontend/src/components/ui/) directory:

1. **Input Component** ([input.tsx](file:///c%3A/Users/GOOD/Music/Devtracker/frontend/src/components/ui/input.tsx)):
   - Animated input field with hover effects
   - Uses Framer Motion for animations
   - Supports all standard HTML input attributes
   - Responsive design with dark mode support

2. **Label Component** ([label.tsx](file:///c%3A/Users/GOOD/Music/Devtracker/frontend/src/components/ui/label.tsx)):
   - Accessible label implementation using Radix UI primitives
   - Supports dark mode styling
   - Compatible with form elements

3. **Animated Theme Toggler** ([animated-theme-toggler.tsx](file:///c%3A/Users/GOOD/Music/Devtracker/frontend/src/components/ui/animated-theme-toggler.tsx)):
   - Smooth theme transition between light and dark modes
   - Uses View Transitions API for fluid animations
   - Integrates with Lucide React icons (Sun/Moon)

4. **Tooltip Component** ([Tooltip.tsx](file:///c%3A/Users/GOOD/Music/Devtracker/frontend/src/components/ui/Tooltip.tsx)):
   - Custom tooltip with position support
   - Responsive design (different positions for mobile/desktop)
   - Custom CSS styling

#### Component Libraries
1. **Bootstrap Components**:
   - Used for layout and some UI elements
   - React Bootstrap integration for React components

2. **Radix UI Primitives**:
   - Accessible UI primitives for building high-quality design systems
   - Used for the Label component

3. **Lucide React Icons**:
   - Consistent icon set throughout the application
   - Used in various components and pages

### Modules & Components
1. **Pages**:
   - [Home](file:///c%3A/Users/GOOD/Music/Devtracker/frontend/src/pages/home/Home.tsx) - Dashboard/home page
   - [Auth Pages](file:///c%3A/Users/GOOD/Music/Devtracker/frontend/src/pages/auth/) - Login, Signup, Organization
   - [Project Pages](file:///c%3A/Users/GOOD/Music/Devtracker/frontend/src/pages/projects/) - Summary, Activities, Board, ListTasks
   - [Messages](file:///c%3A/Users/GOOD/Music/Devtracker/frontend/src/pages/messages/Messages.tsx) - Messaging functionality
   - [Settings](file:///c%3A/Users/GOOD/Music/Devtracker/frontend/src/pages/Settings.tsx) - User settings

2. **Components**:
   - [Navbar](file:///c%3A/Users/GOOD/Music/Devtracker/frontend/src/components/Navbar.tsx) and [SideNavbar](file:///c%3A/Users/GOOD/Music/Devtracker/frontend/src/components/SideNavbar.tsx) - Navigation
   - [ProjectLayout](file:///c%3A/Users/GOOD/Music/Devtracker/frontend/src/components/ProjectLayout.tsx) - Project page layout
   - [ProtectedRoute](file:///c%3A/Users/GOOD/Music/Devtracker/frontend/src/components/ProtectedRoute.tsx) - Authentication guard
   - [Todo Components](file:///c%3A/Users/GOOD/Music/Devtracker/frontend/src/components/) - Task management UI
   - [Chat Components](file:///c%3A/Users/GOOD/Music/Devtracker/frontend/src/components/) - Messaging UI

3. **Services**:
   - [api.ts](file:///c%3A/Users/GOOD/Music/Devtracker/frontend/src/lib/api.ts) - API endpoint utilities

4. **Types**:
   - TypeScript interfaces for all data models

### Build & Development Tools
1. **Vite 7.1.2** - Build tool and development server
2. **ESLint** - Code linting
3. **TypeScript Compiler** - Type checking and compilation
4. **PostCSS & Autoprefixer** - CSS processing
5. **Tailwind CSS** - Styling framework

## Key Features
1. **User Authentication**: JWT-based authentication with login/signup
2. **Project Management**: Create, read, update, delete projects
3. **Task Management**: Task creation and organization with drag-and-drop
4. **Team Collaboration**: Organization and user management
5. **Real-time Messaging**: WebSocket-based chat functionality
6. **Responsive UI**: Mobile-friendly design with Bootstrap and Tailwind
7. **Data Visualization**: Charts and graphs for project metrics
8. **Dark Mode Support**: Theme toggling with animated transitions

## Database Configuration
The project is specifically configured to use Neon PostgreSQL as the sole database backend, with serverless optimizations including:
- SSL-required connections
- Connection pooling tuned for serverless environments
- Auto-suspend friendly configurations

## Environment Configuration
Both frontend and backend use environment variables for configuration:
- Backend: PowerShell scripts for environment setup
- Frontend: [.env](file:///c%3A/Users/GOOD/Music/Devtracker/frontend/.env) file for API endpoints