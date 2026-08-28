# Civic Connect - Public Issue Management System

A comprehensive full-stack application enabling citizens to report public issues, upload images, and track complaint status.

## Features

- 📱 Report public issues (potholes, street lights, water leaks, etc.)
- 📸 Upload images with issue reports
- 📊 Real-time complaint status tracking
- 📈 Interactive dashboards for analytics
- 🔐 Secure authentication with JWT
- 👥 Citizen and Admin portals
- 🎯 Complaint trend monitoring
- 📉 Data-driven insights

## Tech Stack

**Backend:**
- Java 17
- Spring Boot 3.1.5
- Spring Security + JWT
- Spring Data JPA
- MySQL 8.0

**Frontend:**
- React.js 18
- Redux
- Tailwind CSS
- Axios

## Project Structure

```
civic-connect/
├── backend/           # Spring Boot application
├── frontend/          # React.js application
├── docker-compose.yml # Docker setup
└── README.md
```

## Quick Start

### Backend Setup
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### Frontend Setup
```bash
cd frontend
npm install
npm start
```

## Database

MySQL runs on `localhost:3306`
- Database: `civic_connect`
- User: `civic_user`
- Password: `civic_password`

## API Endpoints

### Issues
- `POST /api/issues` - Report new issue
- `GET /api/issues` - Get all issues
- `GET /api/issues/{id}` - Get issue details
- `PUT /api/issues/{id}` - Update issue
- `DELETE /api/issues/{id}` - Delete issue

### Authentication
- `POST /api/auth/register` - Register user
- `POST /api/auth/login` - Login user

### Analytics
- `GET /api/analytics/dashboard` - Dashboard data
- `GET /api/analytics/trends` - Issue trends

## License

MIT License