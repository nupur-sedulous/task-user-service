# Task Management Microservices Application

A full‑stack task management platform built with **Spring Boot microservices** and a **React** frontend.  
Admins can create tasks, assign them to users, and accept/reject submissions.  
Users can view their assigned tasks, mark them as done, and submit GitHub links.

---

## Architecture

The backend is split into independent microservices, each with its own database, communicating via
**Eureka service discovery** and **OpenFeign**.  
All external requests go through an **API Gateway**, which also handles CORS and load‑balancing.
[ React Frontend ] <---> [ API Gateway (5000) ] <---> [ Eureka Server (8070) ]
|
+--> [ User Service (5001) ]
+--> [ Task Service (5002) ]
+--> [ Submission Service (5003) ]

text

---

## Tech Stack

- **Backend**: Spring Boot 4.0.x, Spring Cloud (Eureka, Gateway, OpenFeign), Spring Security, JWT, JPA, MySQL
- **Frontend**: React (Vite), React Router, Axios, plain CSS
- **Build**: Maven (for backend), npm (for frontend)

---

## Features

- **User Management**: Signup, signin with JWT, role‑based access (ADMIN / USER)
- **Task Operations**: Create, update, delete, assign users, filter by status
- **Submission Handling**: Submit GitHub link, accept/decline by admin
- **Client‑side Routing**: Home, DONE, ASSIGNED, NOT ASSIGNED, Admin Panel
- **Real API integration**: (planned – currently frontend uses dummy data for demo)

---

## Project Structure
├── eureka-server/ # Service Registry
├── gateway/ # API Gateway (Spring Cloud Gateway MVC)
├── task-user-service/ # User authentication & profile
├── task-service/ # Task CRUD & assignment
├── task-submission-service/ # Submission handling
└── frontend/ # React + Vite application

text

---

## Getting Started (Local Development)

### Prerequisites
- Java 17+
- MySQL
- Node.js (for frontend)

### 1. Clone the repository
```bash
git clone https://github.com/sid-0220/task-management-backend.git
cd task-management-backend
2. Set up MySQL databases
Create three databases:

sql
CREATE DATABASE task_user_service;
CREATE DATABASE task_service;
CREATE DATABASE task_submission_service;
3. Configure backend
All services use application.yaml. Update database credentials if needed (default root/Dishu@2010).
JWT secret is in task-user-service/.../JwtConstant.java.

4. Build & run the backend services
Start them in this order:

Eureka Server – port 8070

User Service – port 5001

Task Service – port 5002

Submission Service – port 5003

Gateway – port 5000

You can run them via IDE or manually with Maven:

bash
cd <service-folder>
mvn clean install -DskipTests
mvn spring-boot:run
5. Install & start the frontend (dummy‑data version)
bash
cd frontend
npm install
npm run dev
Open http://localhost:5173 to see the interface.

Demo Frontend (Deployed)
A static snapshot of the frontend with dummy data is available at:
https://task-manager-frontend.vercel.app (update with your actual link)

API Endpoints (through Gateway)
Auth
POST /auth/signup

POST /auth/signin

User
GET /api/user/profile

GET /api/user

Tasks
POST /api/tasks

GET /api/tasks

GET /api/tasks/{id}

PUT /api/tasks/{id}

DELETE /api/tasks/{id}

PUT /api/tasks/{id}/user/{userId}/assigned

PUT /api/tasks/{id}/complete

Submissions
POST /api/submissions

GET /api/submissions

GET /api/submissions/{id}

GET /api/submissions/task/{taskId}

PUT /api/submissions/{id}

Important Notes
The frontend currently uses hardcoded dummy data for demonstration. It does not connect to the backend.
To connect it, replace the API calls in frontend/src/api/* with the actual gateway URL and remove dummy data.

All services must be registered in Eureka (check http://localhost:8070) for the Gateway to work.

CORS is configured for http://localhost:5173; update it when deploying.

License
This project is for learning/demo purposes.
