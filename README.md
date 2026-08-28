# Civic Connect - Public Issue Management System

## 📋 Project Overview

Civic Connect is a comprehensive full-stack application designed to empower citizens to report public issues, track their status, and collaborate with government authorities. The platform enables efficient issue management with image uploads, community engagement, and administrative oversight.

## ✨ Features

### For Citizens
- **User Authentication**: Secure registration and login with JWT tokens
- **Issue Reporting**: Report public issues with detailed descriptions, categories, and locations
- **Image Upload**: Attach images to issues for better documentation
- **Issue Tracking**: Monitor the status of reported issues in real-time
- **Community Engagement**: Comment on issues and engage with other citizens
- **Issue Voting**: Upvote/downvote issues to indicate priority and importance
- **Email Notifications**: Receive updates on issue status changes

### For Administrators
- **User Management**: Manage user accounts and assign roles
- **Issue Management**: Assign issues to officials, update status, and resolve issues
- **Analytics Dashboard**: View statistics and system overview
- **Notification System**: Send notifications to relevant stakeholders

## 🏗️ Architecture

### Backend Stack
- **Framework**: Spring Boot 3.1.0
- **Language**: Java 17
- **Database**: MySQL
- **Authentication**: JWT (JSON Web Tokens)
- **Security**: Spring Security with CORS support
- **API Documentation**: Swagger/OpenAPI 3.0
- **Email**: JavaMailSender with SMTP
- **Build Tool**: Maven

### Frontend Stack (To be implemented)
- React.js / Angular
- TypeScript
- Material-UI / Bootstrap
- Axios for API calls

## 📁 Project Structure

```
civic-connect/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/civicconnect/
│   │   │   │   ├── controller/        # REST API endpoints
│   │   │   │   ├── service/           # Business logic
│   │   │   │   ├── model/             # JPA entities
│   │   │   │   ├── repository/        # Data access layer
│   │   │   │   ├── dto/               # Data transfer objects
│   │   │   │   ├── security/          # JWT & Security config
│   │   │   │   ├── exception/         # Exception handlers
│   │   │   │   └── config/            # Configuration classes
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   │       ├── java/com/civicconnect/
│   │       │   ├── service/           # Service tests
│   │       │   ├── controller/        # Controller tests
│   │       │   └── security/          # Security tests
│   │       └── resources/
│   │           └── application-test.properties
│   ├── pom.xml                        # Maven dependencies
│   └── docker-compose.yml             # Docker configuration
└── README.md
```

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- Git

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/Antharyakharvi/civic-connect.git
cd civic-connect
```

2. **Setup Database**
```bash
# Create database
MySQL> CREATE DATABASE civic_connect;

# Update credentials in application.properties
spring.datasource.username=root
spring.datasource.password=your_password
```

3. **Configure Email (Optional)**
```properties
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
```

4. **Build the project**
```bash
cd backend
mvn clean build
```

5. **Run the application**
```bash
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`

### Using Docker

```bash
docker-compose up -d
```

## 📚 API Documentation

Once the application is running, visit:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

## 🔐 Authentication

### Register
```bash
POST /api/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe",
  "phone": "1234567890",
  "city": "New York",
  "state": "NY",
  "address": "123 Main St"
}
```

### Login
```bash
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```

## 📝 API Endpoints

### Issues
- `POST /api/issues` - Create a new issue
- `GET /api/issues` - Get all issues (paginated)
- `GET /api/issues/{id}` - Get issue by ID
- `PUT /api/issues/{id}` - Update issue
- `DELETE /api/issues/{id}` - Delete issue
- `GET /api/issues/status/{status}` - Get issues by status
- `GET /api/issues/category/{category}` - Get issues by category
- `GET /api/issues/priority/{priority}` - Get issues by priority
- `POST /api/issues/{id}/assign` - Assign issue to official
- `POST /api/issues/{id}/resolve` - Mark issue as resolved
- `POST /api/issues/{id}/upvote` - Upvote an issue
- `POST /api/issues/{id}/downvote` - Downvote an issue

### Comments
- `POST /api/comments` - Add comment to issue
- `GET /api/comments/issue/{issueId}` - Get comments for issue
- `PUT /api/comments/{id}` - Update comment
- `DELETE /api/comments/{id}` - Delete comment

### Images
- `POST /api/images/upload` - Upload image for issue
- `DELETE /api/images/{id}` - Delete image

### Admin
- `GET /api/admin/users` - Get all users (Admin only)
- `PUT /api/admin/users/{id}/role` - Update user role (Admin only)
- `DELETE /api/admin/users/{id}` - Delete user (Admin only)
- `GET /api/admin/statistics` - Get system statistics (Admin only)

## 🧪 Testing

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=AuthServiceTest

# Run with coverage
mvn test jacoco:report
```

## 📊 Database Schema

### Users Table
- id (PK)
- email (Unique)
- password (Encrypted)
- firstName
- lastName
- phone
- city
- state
- address
- role (CITIZEN, OFFICER, ADMIN)
- status (ACTIVE, INACTIVE)
- createdAt
- updatedAt

### Issues Table
- id (PK)
- title
- description
- category
- status (OPEN, IN_PROGRESS, RESOLVED)
- priority (LOW, MEDIUM, HIGH)
- latitude
- longitude
- location
- reportedBy (FK -> Users)
- assignedTo (FK -> Users)
- upvotes
- downvotes
- createdAt
- updatedAt
- resolvedAt

### Comments Table
- id (PK)
- content
- issue (FK -> Issues)
- author (FK -> Users)
- createdAt
- updatedAt

### IssueImages Table
- id (PK)
- issue (FK -> Issues)
- fileName
- originalFileName
- filePath
- fileSize
- contentType
- uploadedAt

## 🔧 Configuration Files

### application.properties
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/civic_connect
spring.datasource.username=root
spring.datasource.password=root

app.jwtSecret=mySecretKeyForCivicConnectApplicationJWTTokenGeneration
app.jwtExpirationMs=86400000

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
```

## 📝 License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.

## 👥 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📞 Support

For support, email support@civicconnect.com or open an issue on GitHub.

## 🎯 Roadmap

- [ ] Mobile app (iOS/Android)
- [ ] Real-time notifications with WebSockets
- [ ] Advanced analytics and reporting
- [ ] Integration with mapping services (Google Maps)
- [ ] Multi-language support
- [ ] Machine learning for issue categorization
- [ ] SMS notifications
- [ ] Push notifications

---

**Made with ❤️ by Civic Connect Team**
